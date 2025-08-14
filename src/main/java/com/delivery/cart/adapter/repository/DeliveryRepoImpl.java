package com.delivery.cart.adapter.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.delivery.cart.core.entity.Deliveries;
import com.delivery.cart.core.entity.DeliveryPartnerDetails;
import com.delivery.cart.core.entity.Reviews;
import com.delivery.cart.core.repository.DeliveriesRepository;
import com.delivery.cart.core.repository.RegisterDeliveryDetails;
import com.delivery.cart.core.repository.ReviewAndRatingRepository;

@Repository
public class DeliveryRepoImpl implements DeliveryRepo{
	
	@Autowired
	private RegisterDeliveryDetails vehicleDetails;
	
	@Autowired
	private DeliveriesRepository deliveriesRepository;
	
	@Autowired
	private ReviewAndRatingRepository reviewAndRatingRepository;

	@Override
	public DeliveryPartnerDetails saveVehicleDetails(DeliveryPartnerDetails details) {
		
		return vehicleDetails.save(details);
	}

	@Override
	public Deliveries saveDeliveryDetails(Deliveries details) {
		return deliveriesRepository.save(details);
	}

	@Override
	public Deliveries findById(UUID id) {
		Optional<Deliveries> deliveries = deliveriesRepository.findById(id);
		
		if(deliveries.isPresent()) {
			Deliveries result = deliveries.get();
			return result;
		}
		return null;
	}

	@Override
	public Reviews findReviewByDeliveryId(UUID deliveryId) {
		return reviewAndRatingRepository.findByDeliveryId(deliveryId);
	}

	@Override
	public Reviews save(Reviews review) {
		return reviewAndRatingRepository.save(review);
	}

}
