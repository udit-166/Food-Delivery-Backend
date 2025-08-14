package com.food.restaurant.adapter.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.food.restaurant.core.entity.Restaurant;
import com.food.restaurant.core.entity.RestaurantReviews;

public interface RestaurantRepositories {
	
	Restaurant findByName(String name);
	
	Restaurant save(Restaurant newRestaurant);
	
	List<Restaurant> getAllRestaurant();
	
	Optional<Restaurant> findById(UUID restaunrant_id);
	
	RestaurantReviews saveReviews(RestaurantReviews review);
}
