package com.food.restaurant.adapter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddFoodItemResponse {

	private AddFoodItemDto addFoodItemDto;
	
	private String message;
}
