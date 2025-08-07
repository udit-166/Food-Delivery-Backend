package com.food.restaurant.core.usecase;

import java.util.List;
import java.util.UUID;

import com.food.restaurant.adapter.model.AddFoodItemDto;
import com.food.restaurant.adapter.model.AddFoodItemResponse;
import com.food.restaurant.adapter.model.CategoryMenuDto;
import com.food.restaurant.adapter.model.FoodItemDto;
import com.food.restaurant.core.entity.FoodItem;
import com.food.restaurant.core.entity.Restaurant;

public interface RestaurantUsecase {

	public AddFoodItemResponse addFoodItems(FoodItemDto foodItem); 
	
	public Restaurant createRestaurant(Restaurant restaurant);
	
	public List<Restaurant> getAllRestaurant();
	
	public List<FoodItem> getAllFoodItems();
	
	public Restaurant updateRestaurant (Restaurant restaurant);
	
	public void deleteRestaurant(UUID restaurant_id);
	
	public List<CategoryMenuDto> getMenuOfRestaurant(UUID restaurant_id);
}
