package com.food.restaurant.adapter.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.food.restaurant.adapter.model.AddFoodItemDto;
import com.food.restaurant.adapter.model.AddFoodItemResponse;
import com.food.restaurant.adapter.service.RestaurantService;
import com.food.restaurant.core.entity.FoodItem;
import com.food.restaurant.core.entity.Restaurant;
import com.food.restaurant.core.usecase.RestaurantUsecase;

@Service
public class RestaurantServiceImpl implements RestaurantService{
	
	@Autowired
	private RestaurantUsecase restaurantUsecase;

	@Override
	public AddFoodItemResponse addFoodItems(AddFoodItemDto foodItem) {
		return restaurantUsecase.addFoodItems(foodItem);
	}

	@Override
	public List<Restaurant> getAllRestaurant() {
		
		return restaurantUsecase.getAllRestaurant();
	}

	@Override
	public List<FoodItem> getAllFoodItems() {
		
		return restaurantUsecase.getAllFoodItems();
	}

	@Override
	public Restaurant updateRestaurant(Restaurant restaurant) {
		return restaurantUsecase.updateRestaurant(restaurant);
	}

	@Override
	public void deleteRestaurant(String restaurant_name) {
		restaurantUsecase.deleteRestaurant(restaurant_name);
		
	}

}
