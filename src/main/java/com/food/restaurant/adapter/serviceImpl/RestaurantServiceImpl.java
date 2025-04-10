package com.food.restaurant.adapter.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.food.restaurant.adapter.model.AddFoodItemDto;
import com.food.restaurant.adapter.model.AddFoodItemResponse;
import com.food.restaurant.adapter.service.RestaurantService;
import com.food.restaurant.core.usecase.RestaurantUsecase;

@Service
public class RestaurantServiceImpl implements RestaurantService{
	
	@Autowired
	private RestaurantUsecase restaurantUsecase;

	@Override
	public AddFoodItemResponse addFoodItems(AddFoodItemDto foodItem) {
		return restaurantUsecase.addFoodItems(foodItem);
	}

}
