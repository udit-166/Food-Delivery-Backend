package com.delivery.cart.adapter.models;

import java.util.UUID;

import lombok.Data;

@Data
public class RemoveCartItemRequest {

	private UUID userId;
	
	private UUID foodItemId;
}
