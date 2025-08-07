package com.delivery.cart.core.usecase;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.cart.adapter.mapper.CartMapper;
import com.delivery.cart.adapter.models.CartDto;
import com.delivery.cart.adapter.repository.CartRepo;
import com.delivery.cart.core.models.Cart;

@Service
public class CartUsecaseImpl implements CartUsecase{
	
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private CartMapper cartMapper;

	@Override
	public void addOrUpdateItem(CartDto requestDto) {
		Cart cart = cartMapper.dtoToEntity(requestDto);
		
		cartRepo.saveCart(cart);
		
	}

	@Override
	public CartDto getCart(UUID userId) {
		Cart cart = cartRepo.getCartByUserId(userId);
		
		CartDto cartDto = cartMapper.entityToDto(cart);
		
		return cartDto;
	}

	@Override
	public void removeItem(UUID userId, UUID foodId) {
		cartRepo.removeItem(userId, foodId);
	}

	@Override
	public void clearCart(UUID userId) {
		cartRepo.clearCart(userId);
		
	}

}
