package com.order.track.adapter.respository;

import java.util.UUID;

import com.order.track.core.entity.Order;

public interface OrderRepository {

	Order save(Order order);
	
	Order findById(UUID order_id);
	
	Order findByCustomerId(UUID customer_id);
	
	Order findByRestaurantId(UUID restaurant_id);
}
