package com.food.restaurant.adapter.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class UpdateFoodItemsByRestaurantRequest {
	
	private ArrayList<FoodItemDto> foodItems;

}
