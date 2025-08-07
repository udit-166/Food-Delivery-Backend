package com.delivery.cart.adapter.serviceImpl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.cart.adapter.models.CartDto;
import com.delivery.cart.adapter.service.CartService;
import com.delivery.cart.core.usecase.CartUsecase;


@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private CartUsecase cartUsecase;

	@Override
	public void addOrUpdateItem(CartDto requestDto) {
		cartUsecase.addOrUpdateItem(requestDto);
		
	}

	@Override
	public CartDto getCart(UUID userId) {
		return cartUsecase.getCart(userId);
	}

	@Override
	public void removeItem(UUID userId, UUID foodId) {
		cartUsecase.removeItem(userId, foodId);
		
	}

	@Override
	public void clearCart(UUID userId) {
		cartUsecase.clearCart(userId);
		
	}

}
