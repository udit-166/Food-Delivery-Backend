package com.food.restaurant.adapter.model;

import java.util.UUID;

import com.food.restaurant.core.entity.Categories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFoodItemDto {

	private UUID id;
	
	private String name;
	
	private String description;
	
	private Categories category;
	
	private Integer price;
	
	private Double rating;
	
	private String imageUrl;
	
	private String restaurant_name;
}
