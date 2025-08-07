package com.order.track.adapter.model;

import java.util.UUID;

import lombok.Data;

@Data
public class CancelOrderRequestDto {
	
	private UUID restaurant_id;
	
	private UUID admin_id;
	
	private UUID order_id;

}
