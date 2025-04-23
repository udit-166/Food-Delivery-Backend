package com.order.track.adapter.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.order.track.core.entity.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSummaryDTO {

	private UUID id;
	
	private UUID customerId;     //from auth-service
	
	private UUID restaurantId;    // from  restaurant-service
	
	private OrderStatus status;
	
	private BigDecimal totalPrice;
	
	private Boolean isActive;
	
	private BigDecimal delivery_charge;
	
	private List<OrderItem> items;
}
