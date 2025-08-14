package com.food.restaurant.adapter.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.food.restaurant.core.entity.Restaurant;
import com.food.restaurant.core.entity.RestaurantReviews;
import com.food.restaurant.core.repository.RestaurantRatingRepository;
import com.food.restaurant.core.repository.RestaurantRepository;

@Repository
public class RestaurantRepositoriesImpl implements RestaurantRepositories{
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private RestaurantRatingRepository ratingRepository;

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

	@Override
	public Optional<Restaurant> findById(UUID restaunrant_id) {
		return restaurantRepository.findById(restaunrant_id);
	}

	@Override
	public RestaurantReviews saveReviews(RestaurantReviews review) {
		return ratingRepository.save(review);
	}

}
