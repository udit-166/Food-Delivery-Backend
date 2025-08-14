package com.order.track.adapter.model;

import java.util.UUID;

import lombok.Data;

@Data
public class ReviewPendingResponse {
	
	private UUID orderId;
	
	private UUID restaurantId;

}
