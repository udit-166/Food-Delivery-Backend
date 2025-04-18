package com.order.track.core.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.order.track.core.entity.Order;

public interface OrderRepositories extends JpaRepository<Order, UUID>{

	Order findByCustomerId(UUID customerId);
	
	Order findByRestaurantId(UUID restaurantId);
}
