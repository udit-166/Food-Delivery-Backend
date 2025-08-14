package com.delivery.cart.core.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.cart.adapter.constant.AppConstant;
import com.delivery.cart.adapter.models.AssignDeliverPartnerRequest;
import com.delivery.cart.adapter.models.DeliveryDetailsDto;
import com.delivery.cart.adapter.models.GenericResponse;
import com.delivery.cart.adapter.models.RegisterVehicleDetailsRequest;
import com.delivery.cart.adapter.models.StatusCode;
import com.delivery.cart.adapter.models.SubmitReviewsRequest;
import com.delivery.cart.adapter.models.UpdateStatusResponse;
import com.delivery.cart.adapter.service.DeliveryService;
import com.delivery.cart.core.entity.Deliveries;
import com.delivery.cart.core.entity.DeliveryPartnerDetails;
import com.delivery.cart.core.entity.Reviews;

@RestController
@RequestMapping(AppConstant.DELIVERY_CONTROLLER)
public class DeliveryController {
	
	@Autowired
	private DeliveryService deliveryService;
	
	@PostMapping(AppConstant.REGISTER_VEHICLE)
	public GenericResponse<DeliveryPartnerDetails> registerVehicleDetials(@RequestBody RegisterVehicleDetailsRequest request){
		try {
			if(request == null) {
				return new GenericResponse<>("Can not accept the null data", StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			
			DeliveryPartnerDetails result = deliveryService.registerVehicleDetails(request);
			
			return new GenericResponse<>("Data saved successfully!!", StatusCode.of(HttpStatus.OK), result);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong while registring vehicle details. Please try again!!", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PostMapping(AppConstant.ASSIGN_DELIVERY_PERSON)
	public GenericResponse<Deliveries> assignedDeliveryPerson(@RequestBody AssignDeliverPartnerRequest request){
		try {
			if(request == null) {
				return new GenericResponse<>("Can not accept the null data", StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			
			Deliveries result = deliveryService.assignDeliveryPerson(request);
			
			return new GenericResponse<>("Delivery Person Assigned", StatusCode.of(HttpStatus.OK), result);
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong while assigning the deliver partner. We will assign it soon.", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PatchMapping(AppConstant.UPDATE_DELIVERY_STATUS)
	public GenericResponse<UpdateStatusResponse> updateDeliveryStatus(@RequestParam UUID delivery_id){
		try {
			if(delivery_id==null) {
				return new GenericResponse<>("Can not accept the null data", StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			
			UpdateStatusResponse res = deliveryService.updateStatus(delivery_id);
			
			return new GenericResponse<>("Update the delivery status Successfully!!", StatusCode.of(HttpStatus.OK), res);
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong while updating delivery status.", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@GetMapping(AppConstant.GET_DELIVERY_DETAILS)
	public GenericResponse<DeliveryDetailsDto> getDeliveryDetailsById(@PathVariable UUID delivery_id){
		try {
			if(delivery_id==null) {
				return new GenericResponse<>("Can not accept the null data", StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			
			DeliveryDetailsDto res = deliveryService.getDeliveryDetailsById(delivery_id);
			
			return new GenericResponse<>("Fetched delivery details by id successfully!!", StatusCode.of(HttpStatus.OK), res);
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong while fetching the delivery details by id", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PostMapping(AppConstant.SUBMIT_REVIEWS)
	public GenericResponse<Reviews> submitReview(@RequestBody SubmitReviewsRequest request){
		try {
			if(request == null) {
				return new GenericResponse<>("Payload can not be null", StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			
			Reviews dataAvailable = deliveryService.getReviewsByDeliveryId(request.getDeliveryId());
			
			if(dataAvailable != null) {
				return new GenericResponse<>("Review already submitted!!", StatusCode.of(HttpStatus.ALREADY_REPORTED), dataAvailable);
			}
			
			Reviews result = deliveryService.submitReviewForDelivery(request);
			
			return new GenericResponse<>("Review submitted successfully!!", StatusCode.of(HttpStatus.OK), result);
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong while submitting the reviews", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}

}
