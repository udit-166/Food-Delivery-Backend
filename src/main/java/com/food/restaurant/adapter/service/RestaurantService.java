package com.food.restaurant.adapter.service;

import java.util.List;
import com.food.restaurant.adapter.model.AddFoodItemDto;
import com.food.restaurant.adapter.model.AddFoodItemResponse;
import com.food.restaurant.core.entity.FoodItem;
import com.food.restaurant.core.entity.Restaurant;

public interface RestaurantService {

	public AddFoodItemResponse addFoodItems(AddFoodItemDto foodItem); 
	
	public List<Restaurant> getAllRestaurant();
	
	public List<FoodItem> getAllFoodItems();
	
	public Restaurant updateRestaurant (Restaurant restaurant);
	
	public void deleteRestaurant(String restaurant_name);
	

}
