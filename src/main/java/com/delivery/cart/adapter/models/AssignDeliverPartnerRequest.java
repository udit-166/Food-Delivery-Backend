package com.delivery.cart.adapter.models;

import java.util.UUID;

import com.delivery.cart.core.models.Location;

import lombok.Data;

@Data
public class AssignDeliverPartnerRequest {

	private UUID restaurant_id;
	
	private UUID order_id;
	
	private UUID customer_id;
	
	private Location pickupLocation;
	
	private Location dropLocation;
	
	
}
