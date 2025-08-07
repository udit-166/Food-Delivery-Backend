package com.delivery.cart.adapter.repository;

import java.util.UUID;

import com.delivery.cart.core.models.Cart;

public interface CartRepo {

	public void saveCart(Cart cart);
    public Cart getCartByUserId(UUID userId);
    public void clearCart(UUID userId);
    public void removeItem(UUID userId, UUID foodId);
}
