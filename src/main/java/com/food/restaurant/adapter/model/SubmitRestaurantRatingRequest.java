package com.food.restaurant.adapter.model;

import java.util.ArrayList;
import java.util.UUID;

import lombok.Data;

@Data
public class SubmitRestaurantRatingRequest {
	
	private UUID restaurant_id;
	
	private UUID order_id;
	
	private int rating;
	
	private String review;
	
	private ArrayList<String> imageUrls;

}
