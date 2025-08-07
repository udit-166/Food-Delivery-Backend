package com.delivery.cart.adapter.models;

import java.util.ArrayList;
import java.util.UUID;

import com.delivery.cart.core.models.CartItem;

import lombok.Data;

@Data
public class CartDto {

	private UUID restaurantId;
	private String restaunrantName;
	private ArrayList<CartItem>items;
	private UUID userId;
	private String restaurantPhotoUrl;
	private double totalPrice;
}
