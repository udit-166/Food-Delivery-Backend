package com.order.track.adapter.serviceImpl;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.order.track.adapter.mapper.OrderMapper;
import com.order.track.adapter.model.CountOrderResponse;
import com.order.track.adapter.model.OrderDTO;
import com.order.track.adapter.model.OrderStatus;
import com.order.track.adapter.model.OrderSummaryDTO;
import com.order.track.adapter.model.ReviewPendingResponse;
import com.order.track.adapter.service.OrderService;
import com.order.track.core.entity.Order;
import com.order.track.core.usecase.OrderUsecase;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderUsecase orderUsecase;

	@Autowired
	private OrderMapper orderMapper;
	
	@Override
	public OrderDTO placeOrder(OrderDTO order) {
		Order convertedOrder = orderMapper.dtoToEntity(order);
		Order placedOrder =  orderUsecase.placeOrder(convertedOrder);
		
		return orderMapper.entityToDto(placedOrder);
	}

	@Override
	public OrderDTO getOrderById(UUID order_id) {
		Order order = orderUsecase.getOrderById(order_id);
		
		return orderMapper.entityToDto(order);
	}

	@Override
	public OrderDTO getOrderByCustomerId(UUID customer_id) {
		Order order = orderUsecase.getOrderByCustomerId(customer_id);
		return orderMapper.entityToDto(order);
	}

	@Override
	public OrderDTO getOrderByRestaurantId(UUID restaurant_id) {
		Order order = orderUsecase.getOrderByRestaurantId(restaurant_id);
		return orderMapper.entityToDto(order);
	}

	@Override
	public OrderDTO updateOrderStatus(UUID restaurant, UUID order_id, OrderStatus status) {
		Order order = orderUsecase.updateOrderStatus(restaurant, order_id, status);
		return orderMapper.entityToDto(order);
	}

	@Override
	public Boolean cancelOrder(UUID restaurant_id, UUID admin_id, UUID order_id) {
		
		return orderUsecase.cancelOrder(restaurant_id, admin_id, order_id);
	}

	@Override
	public Boolean requestCancellationOfOrder(UUID customer_id, UUID order_id) {
		return orderUsecase.requestCancellationOfOrder(customer_id, order_id);
	}

	@Override
	public String trackOrder(UUID order_id) {
		return orderUsecase.trackOrder(order_id);
	}

	@Override
	public OrderSummaryDTO orderSummary(UUID order_id) {
		
		
		return orderUsecase.orderSummary(order_id);
	}

	@Override
	public CountOrderResponse countOrderByCustomerId(UUID customer_id) {
		
		Integer orderCount =  orderUsecase.countOrderByCustomerId(customer_id);
		
		CountOrderResponse count = new CountOrderResponse();
		count.setCount(orderCount);
		return count;
	}

	@Override
	public CountOrderResponse countOrderByRestaurantId(UUID restaurant_id) {
		
		Integer orderCount = orderUsecase.countOrderByRestaurantId(restaurant_id);
		CountOrderResponse count = new CountOrderResponse();
		count.setCount(orderCount);
		return count;
	}

	@Override
	public ReviewPendingResponse getLastFiveDaysReviewPending(UUID customerId) {
		return orderUsecase.getLastFiveDaysReviewPending(customerId);
	}

	
}
