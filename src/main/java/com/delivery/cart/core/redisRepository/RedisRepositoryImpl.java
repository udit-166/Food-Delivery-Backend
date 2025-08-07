package com.delivery.cart.core.redisRepository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.delivery.cart.adapter.constant.AppConstant;
import com.delivery.cart.core.models.Cart;
import com.delivery.cart.core.models.CartItem;

@Repository
public class RedisRepositoryImpl implements RedisRepository{

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	private String getKey(UUID userId) {
        return AppConstant.PREFIX+ userId.toString();
    }


	@Override
	public void saveCart(Cart cart) {
		redisTemplate.opsForValue().set(getKey(cart.getUserId()), cart, Duration.ofMinutes(30));
		
		
	}

	@Override
	public Cart getCartByUserId(UUID userId) {
		Object result = redisTemplate.opsForValue().get(getKey(userId));
		
		if (result instanceof Cart) {
            return (Cart) result;
        }
		
		return null;
	}

	@Override
	public void clearCart(UUID userId) {
		redisTemplate.delete(getKey(userId));
		
	}

	@Override
	public void removeItem(UUID userId, UUID foodId) {
		Cart cart = getCartByUserId(userId);
        if (cart == null) return;
        
        List<CartItem> updatedItems = cart.getItems().stream().filter(item -> !item.getFoodId().equals(foodId)).toList();
        cart.setItems(new ArrayList<>(updatedItems));
        cart.setTotalPrice(updatedItems.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum());
        
        saveCart(cart);
		
	}
	
}
