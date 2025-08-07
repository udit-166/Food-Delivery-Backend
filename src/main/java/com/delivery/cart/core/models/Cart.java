package com.delivery.cart.core.models;

import java.util.ArrayList;
import java.util.UUID;

import lombok.Data;

@Data
public class Cart {

	private static final long serialVersionUID = 1L;
	private UUID userId;
	private ArrayList<CartItem> items;
	private double totalPrice;
	private String restaurantId;
	private String restaurantUrl;
	private String restaurantName;
}
