package com.food.restaurant.adapter.repository;

import com.food.restaurant.core.entity.Restaurant;

public interface RestaurantRepositories {
	
	Restaurant findByName(String name);
	
	Restaurant save(Restaurant newRestaurant);
}
