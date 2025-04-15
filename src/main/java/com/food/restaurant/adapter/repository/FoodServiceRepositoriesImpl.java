package com.food.restaurant.adapter.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.food.restaurant.core.entity.FoodItem;
import com.food.restaurant.core.repository.FoodItemRepository;

public class FoodServiceRepositoriesImpl implements FoodItemRepositories{
	
	
	@Autowired
	private FoodItemRepository foodItemRepository;

	@Override
	public FoodItem save(FoodItem foodItem) {
		return foodItemRepository.save(foodItem);
	}

	@Override
	public List<FoodItem> getAllFoodItems() {
		return foodItemRepository.findAll();
	}

}
