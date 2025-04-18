package com.order.track.core.usecase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

import com.order.track.adapter.model.OrderStatus;
import com.order.track.adapter.respository.OrderRepository;
import com.order.track.core.entity.Order;
import com.order.track.core.entity.OrderItem;

import jakarta.transaction.Transactional;

@Service
public class OrderUsecaseImpl implements OrderUsecase{

	
	private OrderRepository orderRepository;
	
	@Override
	@Transactional
	public Order placeOrder(Order order) {
		order.setStatus(OrderStatus.CONFIRMED);
		
		BigDecimal total = BigDecimal.ZERO;
		
		List<OrderItem> processedItems = new ArrayList<>();
		
		for(OrderItem item : order.getItems()) {
			item.setOrder(order);
			BigDecimal price = item.getPrice() != null ? item.getPrice() : BigDecimal.valueOf(100); // mock price
            BigDecimal itemTotal = price.multiply(BigDecimal.valueOf(item.getQuantity()));
            
            item.setPrice(itemTotal);
            
            total = total.add(itemTotal);
            
            processedItems.add(item);
		}
		
		order.setItems(processedItems);
		order.setTotalPrice(total);
		return orderRepository.save(order);
	}

	@Override
	public Order getOrderById(UUID order_id) {
		Order order = orderRepository.findById(order_id);
		return order;
	}

	@Override
	public Order getOrderByCustomerId(UUID customer_id) {
		Order order = orderRepository.findByCustomerId(customer_id);
		return order;
	}

	@Override
	public Order getOrderByRestaurantId(UUID restaurant_id) {
		Order order = orderRepository.findByRestaurantId(restaurant_id);
		return order;
	}

	@Override
	public Order updateOrderStatus(UUID restaurant, UUID order_id, OrderStatus status) {
		
		//pending to check is the restaurant_id is present in restaurant service then only this service works.
		Order orderToUpdate = orderRepository.findById(order_id);
		
		orderToUpdate.setStatus(status);
		
		return orderRepository.save(orderToUpdate);
	}

	@Override
	public Boolean cancelOrder(UUID restaurant_id, UUID admin_id, UUID order_id ) {
		
		// pending to check weather the restaurant_id or admin_id do is present or not.....if either of each do not not present then cancel
		Order orderToCancell = orderRepository.findById(order_id);
		if(orderToCancell == null) {
			return false;
		}
		orderToCancell.setStatus(OrderStatus.CANCELLED); 
		orderRepository.save(orderToCancell);
		return true;
	}

	@Override
	public Boolean requestCancellationOfOrder(UUID customer_id, UUID order_id) {
		
		//pending to check if the customer id is present in the auth-service;
		Order orderRequestToOrder = orderRepository.findById(order_id);
		if(orderRequestToOrder == null) {
			return false;
		}
		orderRequestToOrder.setStatus(OrderStatus.CANCELATION_REQUEST_PENDING);
		orderRepository.save(orderRequestToOrder);
		return true;
	}

	@Override
	public String trackOrder(UUID order_id) {
		
		Order order = orderRepository.findById(order_id);
		
		return order.getStatus().toString();
	}

}
