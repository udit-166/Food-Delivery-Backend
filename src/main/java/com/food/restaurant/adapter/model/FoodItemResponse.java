package com.food.restaurant.adapter.model;

import java.util.List;
import java.util.UUID;

import com.food.restaurant.core.entity.FoodItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemResponse {
	
	private List<FoodItem> food_items;
	
	private UUID restaurant_name;
	
	private UUID category_name;
	
	private String message;
}
