package com.delivery.cart.adapter.repository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.delivery.cart.core.models.Cart;
import com.delivery.cart.core.redisRepository.RedisRepository;

@Repository
public class CartRepoImpl implements CartRepo{

	@Autowired
	private RedisRepository redisRepository;

	@Override
	public void saveCart(Cart cart) {
		redisRepository.saveCart(cart);
	}

	@Override
	public Cart getCartByUserId(UUID userId) {
		return redisRepository.getCartByUserId(userId);
	}

	@Override
	public void clearCart(UUID userId) {
		redisRepository.clearCart(userId);
		
	}

	@Override
	public void removeItem(UUID userId, UUID foodId) {
		redisRepository.removeItem(userId, foodId);
		
	}
}
