package com.food.restaurant.adapter.service;

import java.util.List;
import java.util.UUID;

import com.food.restaurant.adapter.model.AddFoodItemResponse;
import com.food.restaurant.adapter.model.CategoryMenuDto;
import com.food.restaurant.core.entity.FoodItem;
import com.food.restaurant.core.entity.Restaurant;

public interface RestaurantService {

	public AddFoodItemResponse addFoodItems(FoodItem foodItem); 
	
	public List<Restaurant> getAllRestaurant();
	
	public List<FoodItem> getAllFoodItems();
	
	public Restaurant updateRestaurant (Restaurant restaurant);
	
	public void deleteRestaurant(String restaurant_name);
	
	public List<CategoryMenuDto> getMenuOfRestaurant(UUID restaurant_id);
}
