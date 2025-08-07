package com.delivery.cart.core.models;

import java.util.UUID;

import lombok.Data;

@Data
public class CartItem {

	private static final long serialVersionUID = 1L;
	private UUID foodId;
	private String foodName;
	private int quantity;
	private Double price;
}
