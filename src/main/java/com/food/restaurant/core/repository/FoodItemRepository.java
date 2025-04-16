package com.food.restaurant.core.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.restaurant.core.entity.Categories;
import com.food.restaurant.core.entity.FoodItem;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, UUID>{

	List<FoodItem> findByCategory(Categories categories);

	List<FoodItem> findByNameContainingIgnoreCase(String query);
	
	List<FoodItem> findAllByRestaurant_Id(UUID restaurantId);
	
	List<FoodItem> findAllByCategory_Id(UUID categoryId);

}
