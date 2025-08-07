package com.order.track.core.usecase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.order.track.adapter.model.HandleNotificationRequest;
import com.order.track.adapter.model.OrderStatus;
import com.order.track.adapter.model.OrderSummaryDTO;
import com.order.track.adapter.respository.OrderRepository;
import com.order.track.core.entity.Order;
import com.order.track.core.entity.OrderItem;

import jakarta.transaction.Transactional;

@Service
public class OrderUsecaseImpl implements OrderUsecase{

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private KafkaTemplate<String, HandleNotificationRequest> kafkaTemplate;
	
	@Override
	@Transactional
	public Order placeOrder(Order order) {
		order.setStatus(OrderStatus.PLACED);
		
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
		Order result = orderRepository.save(order);
		
//		HandleNotificationRequest request = new HandleNotificationRequest();
//		
//		request.setEmail("udhishahi1606@gmail.com");   //we have to make it dynamics
//		request.setOrderId(result.getId().toString());
//		request.setFcmToken("");
//		request.setPhone("8887943623");
//		
//		kafkaTemplate.send("handle_order_placed", request);
		
		return result;
		
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
		
		HandleNotificationRequest request = new HandleNotificationRequest();
		
		request.setEmail("udhishahi1606@gmail.com");   //we have to make it dynamics
		request.setOrderId(orderToUpdate.getId().toString());
		request.setFcmToken("");
		request.setPhone("8887943623");
		
		 String topic = "";
		    switch (status) {
		        case PLACED:
		            topic = "handle_order_placed";
		            break;
		        case DISPATCHED:
		            topic = "handle_order_dispatched";
		            break;
		        case ASSIGNED_TO_DELIVERY:
		            topic = "handle_order_assigned_to_delivery_person";
		            break;
		        case DELIVERED:
		            topic = "handle_order_delivered";
		            break;
		        default:
		            System.out.println("Unknown status: No notification sent.");
		    }

		    // Send notification to Kafka topic (if topic is not empty)
		    if (!topic.isEmpty()) {
		        kafkaTemplate.send(topic, request);
		    }
		
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
		

		HandleNotificationRequest request = new HandleNotificationRequest();
		
		request.setEmail("udhishahi1606@gmail.com");   //we have to make it dynamics
		request.setOrderId(orderToCancell.getId().toString());
		request.setFcmToken("");
		request.setPhone("8887943623");
		kafkaTemplate.send("handle_order_cancel", request);
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

		HandleNotificationRequest request = new HandleNotificationRequest();
		
		request.setEmail("udhishahi1606@gmail.com");   //we have to make it dynamics
		request.setOrderId(orderRequestToOrder.getId().toString());
		request.setFcmToken("");
		request.setPhone("8887943623");
		
		kafkaTemplate.send("handle_request_order_cancellation", request);
		return true;
	}

	@Override
	public String trackOrder(UUID order_id) {
		
		Order order = orderRepository.findById(order_id);
		
		return order.getStatus().toString();
	}

	@Override
	public OrderSummaryDTO orderSummary(UUID order_id) {
		
		Order order = orderRepository.findById(order_id);
		order.setStatus(OrderStatus.PLACED);
		
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
		BigDecimal deliveryCharge = BigDecimal.TEN;
		
		total = total.add(deliveryCharge);
		
		order.setItems(processedItems);
		order.setTotalPrice(total);
		
		OrderSummaryDTO summary = new OrderSummaryDTO();
		
		summary.setId(order.getId());
		summary.setCustomerId(order.getCustomerId());
		summary.setDelivery_charge(deliveryCharge);
		summary.setIsActive(order.getIsActive());
		summary.setItems(processedItems);
		summary.setRestaurantId(order.getRestaurantId());
		summary.setStatus(order.getStatus());
		summary.setTotalPrice(total);
		
		return summary;
	}

	@Override
	public Integer countOrderByCustomerId(UUID customer_id) {
		
		return orderRepository.countByCustomerId(customer_id);
	}

	@Override
	public Integer countOrderByRestaurantId(UUID restaurant_id) {
		
		return orderRepository.countByRestaurantId(restaurant_id);
	}

}
