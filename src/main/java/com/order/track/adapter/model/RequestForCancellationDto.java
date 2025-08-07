package com.order.track.adapter.model;

import java.util.UUID;

import lombok.Data;

@Data
public class RequestForCancellationDto {
	
	private UUID customer_id;
	
	private UUID order_id;
}
