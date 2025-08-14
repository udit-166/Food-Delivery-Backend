package com.delivery.cart.adapter.models;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitReviewsRequest {
	
	private UUID deliveryId;
	
	private int rating;
	
	private String review;

}
