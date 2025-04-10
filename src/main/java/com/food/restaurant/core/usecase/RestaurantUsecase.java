package com.food.restaurant.core.usecase;

import com.food.restaurant.adapter.model.AddFoodItemDto;
import com.food.restaurant.adapter.model.AddFoodItemResponse;

public interface RestaurantUsecase {

	public AddFoodItemResponse addFoodItems(AddFoodItemDto foodItem); 
}
