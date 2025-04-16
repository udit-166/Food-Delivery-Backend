package com.food.restaurant.adapter.serviceImpl;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

import com.food.restaurant.adapter.service.FoodItemService;
import com.food.restaurant.core.entity.FoodItem;
import com.food.restaurant.core.usecase.FoodItemUsecase;

@Service
public class FoodItemServiceImpl implements FoodItemService{
	
	
	private FoodItemUsecase foodItemUsecase;

	@Override
	public List<FoodItem> getAllFoodItems() {
		
		return foodItemUsecase.getAllFoodItems();
	}

	@Override
	public List<FoodItem> getAllFoodItemOfRestaurant(UUID restaurant_id) {
		return foodItemUsecase.getAllFoodItemOfRestaurant(restaurant_id);
	}

	@Override
	public List<FoodItem> getAllFoodItemOfCategory(UUID category_id) {
		return foodItemUsecase.getAllFoodItemOfCategory(category_id);
	}

	@Override
	public FoodItem getFoodItem(UUID food_item_id) {
		
		return foodItemUsecase.getFoodItem(food_item_id);
	}

	@Override
	public FoodItem updateFoodItem(FoodItem foodItem) {
		return foodItemUsecase.updateFoodItem(foodItem);
	}

	@Override
	public void deleteFoodItem(UUID food_item_id) {
		foodItemUsecase.deleteFoodItem(food_item_id);
		
	}

}
