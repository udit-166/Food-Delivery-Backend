package com.order.track.core.usecase;

import java.util.UUID;

import com.order.track.adapter.model.OrderStatus;
import com.order.track.core.entity.Order;

public interface OrderUsecase {

	public Order placeOrder(Order order);
	
	public Order getOrderById(UUID order_id);
	
	public Order getOrderByCustomerId(UUID customer_id);
	
	public Order getOrderByRestaurantId(UUID restaurant_id);
	
	public Order updateOrderStatus(UUID restaurant, UUID order_id, OrderStatus status);
	
	public Boolean cancelOrder(UUID restaurant_id, UUID admin_id, UUID order_id );
	
	public Boolean requestCancellationOfOrder(UUID customer_id, UUID order_id);
	
	public String trackOrder(UUID order_id);
}
