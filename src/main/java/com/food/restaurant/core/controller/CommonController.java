package com.food.restaurant.core.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.restaurant.adapter.mapper.FoodItemMapper;
import com.food.restaurant.adapter.mapper.RestaurantMapper;
import com.food.restaurant.core.entity.Categories;
import com.food.restaurant.core.entity.FoodItem;
import com.food.restaurant.core.entity.Restaurant;
import com.food.restaurant.core.repository.CategoriesReposiotory;
import com.food.restaurant.core.repository.FoodItemRepository;
import com.food.restaurant.core.repository.RestaurantRepository;

@RestController
@RequestMapping("/api/user")
public class CommonController {
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private CategoriesReposiotory categoriesReposiotory;
	
	@Autowired
	private FoodItemRepository foodItemRepository;
	
	private RestaurantMapper restaurantMapper;
	
	private FoodItemMapper foodItemMapper;
	
	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam("query") String query) {
	    // Check for restaurant
	    Optional<Restaurant> restaurantOpt = restaurantRepository.findByNameContainingIgnoreCase(query);
	    if (restaurantOpt.isPresent()) {
	        Restaurant restaurant = restaurantOpt.get();
	        return ResponseEntity.ok(restaurantMapper.entityToDto(restaurant)); // includes foodItems
	    }

	    // Check for category
	    Optional<Categories> categoryOpt = categoriesReposiotory.findByCategoryContainingIgnoreCase(query);
	    if (categoryOpt.isPresent()) {
	        List<FoodItem> foodItems = foodItemRepository.findByCategory(categoryOpt.get());
	        return ResponseEntity.ok(foodItemMapper.entityToDto(foodItems));
	    }

	    // Check for food name
	    List<FoodItem> foodItems = foodItemRepository.findByNameContainingIgnoreCase(query);
	    if (!foodItems.isEmpty()) {
	        return ResponseEntity.ok(foodItemMapper.entityToDto(foodItems));
	    }

	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No match found");
	}

	
}
