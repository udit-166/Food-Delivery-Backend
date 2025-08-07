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

import com.food.restaurant.adapter.constant.AppConstant;
import com.food.restaurant.adapter.mapper.FoodItemMapper;
import com.food.restaurant.adapter.mapper.RestaurantMapper;
import com.food.restaurant.adapter.model.GenericResponse;
import com.food.restaurant.adapter.model.StatusCode;
import com.food.restaurant.core.entity.Categories;
import com.food.restaurant.core.entity.FoodItem;
import com.food.restaurant.core.entity.Restaurant;
import com.food.restaurant.core.repository.CategoriesReposiotory;
import com.food.restaurant.core.repository.FoodItemRepository;
import com.food.restaurant.core.repository.RestaurantRepository;

@RestController
@RequestMapping(AppConstant.COMMON_CONTROLLER)
public class CommonController {
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private CategoriesReposiotory categoriesReposiotory;
	
	@Autowired
	private FoodItemRepository foodItemRepository;
	
	@Autowired
	private RestaurantMapper restaurantMapper;
	
	
	@GetMapping(AppConstant.SEARCH)
	public GenericResponse<?> search(@RequestParam("query") String query) {
	    // Check for restaurant
	    Optional<Restaurant> restaurantOpt = restaurantRepository.findByNameContainingIgnoreCase(query);
	    if (restaurantOpt.isPresent()) {
	        Restaurant restaurant = restaurantOpt.get();
	        return new GenericResponse<>("Filter applied successfully!!",StatusCode.of(HttpStatus.OK), restaurantMapper.entityToDto(restaurant)); // includes foodItems
	    }

	    // Check for category
	    Optional<Categories> categoryOpt = categoriesReposiotory.findByCategoryContainingIgnoreCase(query);
	    if (categoryOpt.isPresent()) {
	        List<FoodItem> foodItems = foodItemRepository.findByCategory(categoryOpt.get());
//	        return ResponseEntity.ok(foodItemMapper.entityToDto(foodItems));
	        return new GenericResponse<>("Category found!!",StatusCode.of(HttpStatus.OK), categoryOpt.get());
	    }

	    // Check for food name
	    List<FoodItem> foodItems = foodItemRepository.findByNameContainingIgnoreCase(query);
	    if (!foodItems.isEmpty()) {
	       // return ResponseEntity.ok(foodItemMapper.entityToDto(foodItems));
	    	
	    	return new GenericResponse<>("Food item fetched successfully!!", StatusCode.of(HttpStatus.OK), foodItems);
	    }

	    return new GenericResponse<>("No match found!!", StatusCode.of(HttpStatus.NOT_FOUND), null);
	}

	
}
