package com.delivery.cart.adapter.service;

import java.util.UUID;

import com.delivery.cart.adapter.models.CartDto;


public interface CartService {

	public void addOrUpdateItem(CartDto requestDto);
	
	public CartDto getCart(UUID userId);
	
	public void removeItem(UUID userId, UUID foodId);
	
	public void clearCart (UUID userId);
}
