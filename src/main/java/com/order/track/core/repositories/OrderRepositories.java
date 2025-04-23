package com.order.track.core.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order.track.adapter.model.OrderStatus;
import com.order.track.core.entity.Order;

public interface OrderRepositories extends JpaRepository<Order, UUID>{

	Order findByCustomerId(UUID customerId);
	
	Order findByRestaurantId(UUID restaurantId);
	
	List<Order> findByStatus(OrderStatus status);
	
	int countByCustomerId(UUID customerId);

    int countByRestaurantId(UUID restaurantId);
}
