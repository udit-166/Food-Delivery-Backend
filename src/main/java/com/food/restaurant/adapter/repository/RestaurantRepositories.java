package com.food.restaurant.adapter.repository;

import java.util.List;

import com.food.restaurant.core.entity.Restaurant;

public interface RestaurantRepositories {
	
	Restaurant findByName(String name);
	
	Restaurant save(Restaurant newRestaurant);
	
	List<Restaurant> getAllRestaurant();
}
