package com.food.restaurant.adapter.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class RestaurantDto {

	private UUID id;
    private String name;
    private String image_url;
    private Double averageRating;
    private Integer totalRating;
    private List<FoodItemDto> foodItems;
    private String restaurant_email;
	
	private String customer_care_number;

	private String opening_time;
	
	private String closing_time;
	
	private UUID user_id;
	
	private Boolean is_active;
	
	private ArrayList<UUID> categoriesId;
}
