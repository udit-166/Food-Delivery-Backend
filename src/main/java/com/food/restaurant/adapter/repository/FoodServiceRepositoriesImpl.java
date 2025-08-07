package com.food.restaurant.adapter.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.food.restaurant.core.entity.FoodItem;
import com.food.restaurant.core.repository.FoodItemRepository;

@Repository
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

	@Override
	public List<FoodItem> findAllByRestaurant_Id(UUID restaurantId) {
		
		return foodItemRepository.findAllByRestaurant_Id(restaurantId);
	}

	@Override
	public List<FoodItem> findAllByCategory_Id(UUID categoryId) {
		return foodItemRepository.findAllByCategory_Id(categoryId);
	}

	@Override
	public FoodItem findById(UUID food_item_id) {
		Optional<FoodItem> food = foodItemRepository.findById(food_item_id);
		
		if(food.isPresent()) {
			FoodItem result = food.get();
			return result;
		}
		return null;
	}

}
