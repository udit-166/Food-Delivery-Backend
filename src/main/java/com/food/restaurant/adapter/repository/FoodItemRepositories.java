package com.food.restaurant.adapter.repository;

import java.util.List;
import java.util.UUID;

import com.food.restaurant.core.entity.FoodItem;

public interface FoodItemRepositories {

	FoodItem save(FoodItem foodItem);
	
	List<FoodItem> getAllFoodItems();
	List<FoodItem> findAllByRestaurant_Id(UUID restaurantId);
	List<FoodItem> findAllByCategory_Id(UUID categoryId);
	
	FoodItem findById(UUID food_item_id);
}
