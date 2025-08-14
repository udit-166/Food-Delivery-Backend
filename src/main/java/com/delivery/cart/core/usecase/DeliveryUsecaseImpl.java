package com.delivery.cart.core.usecase;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.comparator.Comparators;

import com.delivery.cart.adapter.constant.AppConstant;
import com.delivery.cart.adapter.models.AssignDeliverPartnerRequest;
import com.delivery.cart.adapter.models.DeliveryDetailsDto;
import com.delivery.cart.adapter.models.DeliveryStatus;
import com.delivery.cart.adapter.models.OrderStatus;
import com.delivery.cart.adapter.models.OrderStatusUpdateEvent;
import com.delivery.cart.adapter.models.PartnerEta;
import com.delivery.cart.adapter.models.RegisterVehicleDetailsRequest;
import com.delivery.cart.adapter.models.SubmitReviewsRequest;
import com.delivery.cart.adapter.models.UpdateStatusResponse;
import com.delivery.cart.adapter.repository.DeliveryRepo;
import com.delivery.cart.core.entity.Deliveries;
import com.delivery.cart.core.entity.DeliveryPartnerDetails;
import com.delivery.cart.core.entity.Reviews;
import com.delivery.cart.core.models.VerificationStatus;

@Service
public class DeliveryUsecaseImpl implements DeliveryUsecase{
	
	@Autowired
	private DeliveryRepo deliveryRepo;
	
	@Autowired
	private CommonUsecase commonUsecase;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private KafkaTemplate<String, OrderStatusUpdateEvent> kafkaTemplate;
	

	@Override
	public DeliveryPartnerDetails  registerVehicleDetails(RegisterVehicleDetailsRequest request) {
		try {
			DeliveryPartnerDetails registerDetails = new DeliveryPartnerDetails();
			
			registerDetails.setDelivery_partner_id(request.getDelivery_partner_id());
			registerDetails.setDriving_license_doc(request.getDriving_license_doc());
			registerDetails.setDriving_license_name(request.getDriving_license_name());
			registerDetails.setIs_validation(true);
			registerDetails.setRc_book_doc(request.getRc_book_doc());
			registerDetails.setStatus(VerificationStatus.PENDING);
			registerDetails.setVehicle_number(request.getVehicle_number());
			registerDetails.setVehicle_type(request.getVehicle_type());
			
			DeliveryPartnerDetails result = deliveryRepo.saveVehicleDetails(registerDetails);
			
			commonUsecase.setPartnerOnline(request.getDelivery_partner_id().toString(), request.getLat(), request.getLng(), request.getName());
			
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Deliveries assignDeliveryPerson(AssignDeliverPartnerRequest request) {
		
		String orderLockKey = String.format(AppConstant.ORDER_LOCK_PREFIX, request.getOrder_id());
		
		//try to find order lock
		if(!commonUsecase.acquireLocked(orderLockKey, 30)) {
			throw new IllegalStateException("Order is being processed by another worker");
		}
		try {
			int attempts = commonUsecase.getAttemptsCount(request.getOrder_id());
			System.out.println("The attempts are>>>>>>"+attempts);
			double radius = commonUsecase.computeRadiusForAttempt(attempts);
			
			System.out.println("The radius are>>>>>>"+radius);
			
			List<String> geoDeliveryPersons = commonUsecase.fetchGeoDeliveryPersons(request.getPickupLocation().getLatitude(), request.getPickupLocation().getLongitude(), radius, AppConstant.MAX_CANDIDATE_LIMITS);
			
			System.out.println("Geo candidates: " + geoDeliveryPersons);
			
			if(geoDeliveryPersons.isEmpty()) {
				
				//schedule retry
				commonUsecase.scheduleRetryToSearchDeliveryPerson(request);
				return null;
			}
			
			//call google distance matrix for top candidate
			
			List<PartnerEta> partnerEtas = commonUsecase.fetcheEtaFromGoogle(request, geoDeliveryPersons);
			
			// sorting by eta and trying to assign the delivery person
			
			System.out.println("Geo ETA candidates: " + partnerEtas);
			
			partnerEtas.sort(Comparator.comparingLong(PartnerEta::getEtaSeconds));
			
			System.out.println("Geo ETA candidates after sorting" + partnerEtas);
			
			for( PartnerEta p : partnerEtas) {
				if(commonUsecase.isDeliveryPartnerAvailable(p.getPartnerId())) {
					boolean assigned = commonUsecase.tryAtomicAssignDeliveryPartner(request.getOrder_id(), p.getPartnerId());
					System.out.println("the assined value is: " + assigned);
					if(assigned) {
						Deliveries delivery = new Deliveries();
						
						delivery.setOrder_id(request.getOrder_id());
						delivery.setRestuarant_id(request.getRestaurant_id());
						delivery.setCustomer_id(request.getCustomer_id());
						delivery.setDelivery_partner_id(UUID.fromString(p.getPartnerId()));
						delivery.setDelivery_partner_name(commonUsecase.fetchDeliveryPartnerName(p.getPartnerId()));
						delivery.setStatus(DeliveryStatus.ASSIGNED);
						delivery.setPickUpLocation(request.getPickupLocation());
						delivery.setDropLocation(request.getDropLocation());
						delivery.setAssignDateTime(LocalDateTime.now());
						
						Deliveries result = deliveryRepo.saveDeliveryDetails(delivery);
						
						commonUsecase.markPartnerBusy(p.getPartnerId(), request.getOrder_id());
						
						kafkaTemplate.send("order-status-update",
					            new OrderStatusUpdateEvent(delivery.getOrder_id(), OrderStatus.ASSIGNED_TO_DELIVERY, delivery.getRestuarant_id()));
						
						return result;
					}
				}
			}
			return null;

		} catch (Exception e) {
			commonUsecase.scheduleRetryToSearchDeliveryPerson(request);
			return null;
		}
		finally {
			commonUsecase.releaseLock(orderLockKey);
		}
	}

	@Override
	public UpdateStatusResponse updateStatus(UUID delivery_id) {
		Deliveries delivery = deliveryRepo.findById(delivery_id);

		    // Example: Move to next status
		    DeliveryStatus nextStatus;
		    switch (delivery.getStatus()) {
		        case ASSIGNED:
		            nextStatus = DeliveryStatus.PICKED_UP;
		            break;
		        case PICKED_UP:
		            nextStatus = DeliveryStatus.DELIVERED;
		            break;
		        default:
		            throw new IllegalStateException("Cannot update status from: " + delivery.getStatus());
		    }

		    delivery.setStatus(nextStatus);
		    if(nextStatus == DeliveryStatus.PICKED_UP) {
		    	delivery.setPickUpDateTime(LocalDateTime.now());
		    }
		    else {
		    	delivery.setDeliveryDateTime(LocalDateTime.now());
		    }
		    deliveryRepo.saveDeliveryDetails(delivery);

		    // If delivered, mark partner as available in Redis
		    if (nextStatus == DeliveryStatus.DELIVERED) {
		        String key = String.format(AppConstant.PARTNER_STATUS_PREFIX, delivery.getDelivery_partner_id());
		        redisTemplate.opsForValue().set(key, "AVAILABLE", Duration.ofHours(12));
		    }
		    
		    OrderStatus orderStatus = (nextStatus == DeliveryStatus.DELIVERED)
		            ? OrderStatus.DELIVERED
		            : OrderStatus.DISPATCHED;
		    
		    kafkaTemplate.send("order-status-update",
		            new OrderStatusUpdateEvent(delivery.getOrder_id(), orderStatus, delivery.getRestuarant_id()));
		  UpdateStatusResponse res = new UpdateStatusResponse();
		  res.setDeliveries(delivery);
		  return res;
	}

	@Override
	public DeliveryDetailsDto getDeliveryDetailsById(UUID delivery_id) {
		 Deliveries delivery = deliveryRepo.findById(delivery_id);

			    // Fetch partner details from Redis
			    String partnerName = redisTemplate.opsForValue().get(
			        String.format(AppConstant.PARTNER_NAME_KEY, delivery.getDelivery_partner_id())
			    );
			    String partnerStatus = redisTemplate.opsForValue().get(
			        String.format(AppConstant.PARTNER_STATUS_PREFIX, delivery.getDelivery_partner_id())
			    );
			    
			    Reviews review = getReviewsByDeliveryId(delivery_id);
			    
			    DeliveryDetailsDto result =  new DeliveryDetailsDto();	
			    
			    result.setId(delivery_id);
			    result.setCustomer_id(delivery.getCustomer_id());
			    result.setDelivery_partner_id(delivery.getDelivery_partner_id());
			    result.setDelivery_partner_name(delivery.getDelivery_partner_name());
			    result.setOrder_id(delivery.getOrder_id());
			    result.setRestuarant_id(delivery.getRestuarant_id());
			    result.setStatus(delivery.getStatus());
			    result.setAssignDateTime(delivery.getAssignDateTime());
			    result.setDeliveryDateTime(delivery.getDeliveryDateTime());
			    result.setDropLocation(delivery.getDropLocation());
			    result.setPickUpDateTime(delivery.getPickUpDateTime());
			    result.setPickUpLocation(delivery.getPickUpLocation());
			    if(review != null) {
			    	result.setRating(review.getRating());
			    	result.setReview(review.getReviews());
			    }
			    
			    return result;
			    
	}

	@Override
	public Reviews getReviewsByDeliveryId(UUID delivery_id) {
		Reviews result = deliveryRepo.findReviewByDeliveryId(delivery_id);
		
		return result;
	}

	@Override
	public Reviews submitReviewForDelivery(SubmitReviewsRequest request) {
		Reviews result = new Reviews();
		result.setDeliveryId(request.getDeliveryId());
		result.setRating(request.getRating());
		result.setReviews(request.getReview());
		
		Reviews res = deliveryRepo.save(result);
		
		return res;
	}

}
