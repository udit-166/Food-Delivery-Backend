package com.delivery.cart.adapter.models;

import java.util.ArrayList;
import java.util.UUID;

import com.delivery.cart.core.models.CartItem;

import lombok.Data;

@Data
public class CreateCartRequestDto {

	private UUID userId;
	private UUID restaurantId;
	private String restaurantName;
	private String restaurantUrl;
	
	private ArrayList<CartItem> item;
	
	private Double totalPrice;
}
