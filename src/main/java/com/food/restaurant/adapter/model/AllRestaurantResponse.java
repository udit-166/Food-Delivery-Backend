package com.food.restaurant.adapter.model;

import java.util.List;

import com.food.restaurant.core.entity.Restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllRestaurantResponse {

	private List<Restaurant> allRestaurant;
	
	private String message;
}
