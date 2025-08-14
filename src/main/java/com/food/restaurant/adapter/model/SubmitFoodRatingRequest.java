package com.food.restaurant.adapter.model;

import java.util.UUID;

import lombok.Data;

@Data
public class SubmitFoodRatingRequest {
	
	private UUID foodItemId;
	
	private int rating;

}
