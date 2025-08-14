package com.delivery.cart.adapter.service;

import java.util.UUID;

import com.delivery.cart.adapter.models.AssignDeliverPartnerRequest;
import com.delivery.cart.adapter.models.DeliveryDetailsDto;
import com.delivery.cart.adapter.models.RegisterVehicleDetailsRequest;
import com.delivery.cart.adapter.models.SubmitReviewsRequest;
import com.delivery.cart.adapter.models.UpdateStatusResponse;
import com.delivery.cart.core.entity.Deliveries;
import com.delivery.cart.core.entity.DeliveryPartnerDetails;
import com.delivery.cart.core.entity.Reviews;

public interface DeliveryService {
	
	public DeliveryPartnerDetails registerVehicleDetails(RegisterVehicleDetailsRequest request);
	
	public Deliveries assignDeliveryPerson(AssignDeliverPartnerRequest request);
	
	public UpdateStatusResponse updateStatus(UUID delivery_id);
	
	public DeliveryDetailsDto getDeliveryDetailsById(UUID delivery_id);
	
	public Reviews getReviewsByDeliveryId(UUID delivery_id);
	
	public Reviews submitReviewForDelivery(SubmitReviewsRequest delivery_id);
	

}
