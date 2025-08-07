package com.order.track.adapter.service;

import java.util.UUID;
import com.order.track.adapter.model.CountOrderResponse;
import com.order.track.adapter.model.OrderDTO;
import com.order.track.adapter.model.OrderStatus;
import com.order.track.adapter.model.OrderSummaryDTO;

public interface OrderService {

	public OrderSummaryDTO orderSummary(UUID order_id);
	
	public OrderDTO placeOrder(OrderDTO order);
	
	public OrderDTO getOrderById(UUID order_id);
	
	public OrderDTO getOrderByCustomerId(UUID customer_id);
	
	public OrderDTO getOrderByRestaurantId(UUID restaurant_id);
	
	public OrderDTO updateOrderStatus(UUID restaurant, UUID order_id, OrderStatus status);
	
	public Boolean cancelOrder(UUID restaurant_id, UUID admin_id, UUID order_id );
	
	public Boolean requestCancellationOfOrder(UUID customer_id, UUID order_id);
	
	public String trackOrder(UUID order_id);
	
	public CountOrderResponse countOrderByCustomerId(UUID customer_id);
	
	public CountOrderResponse countOrderByRestaurantId(UUID restaurant_id);
	
}
