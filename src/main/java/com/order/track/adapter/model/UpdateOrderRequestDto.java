package com.order.track.adapter.model;

import java.util.UUID;

import lombok.Data;

@Data
public class UpdateOrderRequestDto {

	private UUID restaurant_id;
	
	private UUID order_id;
	
	private OrderStatus status;
}
