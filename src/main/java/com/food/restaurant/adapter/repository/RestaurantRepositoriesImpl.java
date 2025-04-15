package com.food.restaurant.adapter.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.food.restaurant.core.entity.Restaurant;
import com.food.restaurant.core.repository.RestaurantRepository;

public class RestaurantRepositoriesImpl implements RestaurantRepositories{
	
	@Autowired
	private RestaurantRepository restaurantRepository;

	@Override
	public Restaurant findByName(String name) {
		
		return restaurantRepository.findByName(name);
	}

	@Override
	public Restaurant save(Restaurant newRestaurant) {
		return restaurantRepository.save(newRestaurant);
	}

	@Override
	public List<Restaurant> getAllRestaurant() {
		return restaurantRepository.findAll();
	}

}
