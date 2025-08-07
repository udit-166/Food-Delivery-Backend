package com.order.track.adapter.model;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

	private UUID foodItemId;
	
	private String food_name;
	
	private Integer quantity;
	
	private BigDecimal price;
	
	private UUID delivery_id;
}
