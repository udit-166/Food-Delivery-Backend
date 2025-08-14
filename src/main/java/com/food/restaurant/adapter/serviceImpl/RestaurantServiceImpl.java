package com.food.restaurant.adapter.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.food.restaurant.adapter.model.AddFoodItemResponse;
import com.food.restaurant.adapter.model.CategoryMenuDto;
import com.food.restaurant.adapter.model.FoodItemDto;
import com.food.restaurant.adapter.model.SubmitRestaurantRatingRequest;
import com.food.restaurant.adapter.service.RestaurantService;
import com.food.restaurant.core.entity.FoodItem;
import com.food.restaurant.core.entity.Restaurant;
import com.food.restaurant.core.entity.RestaurantReviews;
import com.food.restaurant.core.usecase.RestaurantUsecase;

@Service
public class RestaurantServiceImpl implements RestaurantService{
	
	@Autowired
	private RestaurantUsecase restaurantUsecase;

	@Override
	public AddFoodItemResponse addFoodItems(FoodItemDto foodItem) {
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
	public void deleteRestaurant(UUID restaurant_id) {
		restaurantUsecase.deleteRestaurant(restaurant_id);
		
	}

	@Override
	public List<CategoryMenuDto> getMenuOfRestaurant(UUID restaurant_id) {
		return restaurantUsecase.getMenuOfRestaurant(restaurant_id);
	}

	@Override
	public Restaurant createRestaurant(Restaurant restaurant) {
		return restaurantUsecase.createRestaurant(restaurant);
	}

	@Override
	public Boolean makeCloseOrOpenRestaurant(UUID restaurant_id) {
		
		return restaurantUsecase.makeCloseOrOpenRestaurant(restaurant_id);
	}

	@Override
	public Boolean updateFoodItems(ArrayList<FoodItemDto> foodItems) {
		return restaurantUsecase.updateFoodItems(foodItems);
	}

	@Override
	public RestaurantReviews submitRating(SubmitRestaurantRatingRequest request) {
		return restaurantUsecase.submitRating(request);
	}

}
