package com.food.restaurant.adapter.service;

import com.food.restaurant.adapter.model.AddFoodItemDto;
import com.food.restaurant.adapter.model.AddFoodItemResponse;

public interface RestaurantService {

	public AddFoodItemResponse addFoodItems(AddFoodItemDto foodItem); 
}
