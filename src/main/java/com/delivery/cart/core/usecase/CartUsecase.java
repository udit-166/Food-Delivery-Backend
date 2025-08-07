package com.delivery.cart.core.usecase;

import java.util.UUID;

import com.delivery.cart.adapter.models.CartDto;


public interface CartUsecase {
	
	public void addOrUpdateItem(CartDto requestDto);
	
	public CartDto getCart(UUID userId);
	
	public void removeItem(UUID userId, UUID foodId);
	
	public void clearCart (UUID userId);

}
