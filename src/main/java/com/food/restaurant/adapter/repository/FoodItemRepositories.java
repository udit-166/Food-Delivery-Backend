package com.food.restaurant.adapter.repository;

import java.util.List;

import com.food.restaurant.core.entity.FoodItem;

public interface FoodItemRepositories {

	FoodItem save(FoodItem foodItem);
	
	List<FoodItem> getAllFoodItems();
}
