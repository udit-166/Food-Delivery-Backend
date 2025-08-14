package com.food.restaurant.adapter.service;

import java.util.List;
import java.util.UUID;

import com.food.restaurant.adapter.model.FoodItemDto;
import com.food.restaurant.adapter.model.SubmitFoodRatingRequest;
import com.food.restaurant.core.entity.FoodItem;

public interface FoodItemService {

	public List<FoodItem> getAllFoodItems();
	
	public List<FoodItem> getAllFoodItemOfRestaurant(UUID restaurant_id);
	
	public List<FoodItem> getAllFoodItemOfCategory(UUID category_id);
	
	public FoodItem getFoodItem(UUID food_item_id);
	
	public FoodItem updateFoodItem(FoodItemDto foodItem);
	
	public void deleteFoodItem(UUID food_item_id);
	
	public Boolean submitFoodRating(SubmitFoodRatingRequest request);
}
