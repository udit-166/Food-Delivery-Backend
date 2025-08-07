package com.food.restaurant.adapter.model;

import java.util.UUID;

import com.food.restaurant.core.entity.Categories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

	private UUID id;
	
	private Categories category;
	
}
