package com.delivery.cart.adapter.models;

import java.time.LocalDateTime;
import java.util.UUID;

import com.delivery.cart.core.models.Location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDetailsDto {

	private UUID id;
	
	private UUID restuarant_id;
	
	private UUID customer_id;
	
	private UUID delivery_partner_id;
	
	private String delivery_partner_name;
	
	private UUID order_id;
	
	private DeliveryStatus status;
	
	
	private Location pickUpLocation;
	
	private Location dropLocation;
	
	
	private LocalDateTime assignDateTime;
	
	private LocalDateTime pickUpDateTime;
	
	private LocalDateTime deliveryDateTime;
	
	private int rating;
	
	private String review;
	
}
