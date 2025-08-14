package com.delivery.cart.adapter.repository;

import java.util.UUID;

import com.delivery.cart.core.entity.Deliveries;
import com.delivery.cart.core.entity.DeliveryPartnerDetails;
import com.delivery.cart.core.entity.Reviews;

public interface DeliveryRepo {
	
	public DeliveryPartnerDetails saveVehicleDetails(DeliveryPartnerDetails details);
	
	public Deliveries saveDeliveryDetails(Deliveries details);
	
	public Deliveries findById(UUID id);
	
	public Reviews findReviewByDeliveryId(UUID deliveryId);
	
	public Reviews save(Reviews review);

}
