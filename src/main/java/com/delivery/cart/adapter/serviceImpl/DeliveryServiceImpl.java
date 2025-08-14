package com.delivery.cart.adapter.serviceImpl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.cart.adapter.models.AssignDeliverPartnerRequest;
import com.delivery.cart.adapter.models.DeliveryDetailsDto;
import com.delivery.cart.adapter.models.RegisterVehicleDetailsRequest;
import com.delivery.cart.adapter.models.SubmitReviewsRequest;
import com.delivery.cart.adapter.models.UpdateStatusResponse;
import com.delivery.cart.adapter.service.DeliveryService;
import com.delivery.cart.core.entity.Deliveries;
import com.delivery.cart.core.entity.DeliveryPartnerDetails;
import com.delivery.cart.core.entity.Reviews;
import com.delivery.cart.core.usecase.DeliveryUsecase;

@Service
public class DeliveryServiceImpl implements DeliveryService{
	
	@Autowired
	private DeliveryUsecase deliveryUsecase;

	@Override
	public DeliveryPartnerDetails registerVehicleDetails(RegisterVehicleDetailsRequest request) {
		
		return deliveryUsecase.registerVehicleDetails(request);
	}

	@Override
	public Deliveries assignDeliveryPerson(AssignDeliverPartnerRequest request) {
		return deliveryUsecase.assignDeliveryPerson(request);
		
	}

	@Override
	public UpdateStatusResponse updateStatus(UUID delivery_id) {
		return deliveryUsecase.updateStatus(delivery_id);
	}

	@Override
	public DeliveryDetailsDto getDeliveryDetailsById(UUID delivery_id) {
		return deliveryUsecase.getDeliveryDetailsById(delivery_id);
	}

	@Override
	public Reviews getReviewsByDeliveryId(UUID delivery_id) {

		return deliveryUsecase.getReviewsByDeliveryId(delivery_id);
	}

	@Override
	public Reviews submitReviewForDelivery(SubmitReviewsRequest request) {
		return deliveryUsecase.submitReviewForDelivery(request);
	}

}
