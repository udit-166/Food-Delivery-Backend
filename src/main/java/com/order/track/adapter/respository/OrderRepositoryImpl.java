package com.order.track.adapter.respository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.order.track.adapter.model.OrderStatus;
import com.order.track.core.entity.Order;
import com.order.track.core.repositories.OrderRepositories;

@Repository
public class OrderRepositoryImpl implements OrderRepository{
	
	@Autowired
	private OrderRepositories orderRepositories;

	@Override
	public Order save(Order order) {
		return orderRepositories.save(order);
	}

	@Override
	public Order findById(UUID order_id) {
		Optional<Order> order = orderRepositories.findById(order_id);
		if(order.isPresent()) {
			Order result = order.get();
			return result;
		}
		return null;
	}

	@Override
	public Order findByCustomerId(UUID customer_id) {
		Order order = orderRepositories.findByCustomerId(customer_id);
		return order;
	}

	@Override
	public Order findByRestaurantId(UUID restaurant_id) {
		Order order = orderRepositories.findByRestaurantId(restaurant_id);
		return order;
	}

	@Override
	public Integer countByCustomerId(UUID customerId) {
		
		return orderRepositories.countByCustomerId(customerId);
	}

	@Override
	public Integer countByRestaurantId(UUID restaurantId) {
		
		return orderRepositories.countByRestaurantId(restaurantId);
	}

	@Override
	public List<Order> findByStatus(OrderStatus status) {
		
		return orderRepositories.findByStatus(status);
	}

}
