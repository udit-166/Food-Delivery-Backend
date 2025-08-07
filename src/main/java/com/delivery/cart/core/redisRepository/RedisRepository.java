package com.delivery.cart.core.redisRepository;

import java.util.UUID;

import com.delivery.cart.core.models.Cart;

public interface RedisRepository {

	void saveCart(Cart cart);
    Cart getCartByUserId(UUID userId);
    void clearCart(UUID userId);
    void removeItem(UUID userId, UUID foodId);
}
