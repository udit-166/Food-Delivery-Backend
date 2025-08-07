package com.food.restaurant.adapter.model;

import java.util.UUID;

import lombok.Data;

@Data
public class UpdateCategoryRequestDto {
	
	private UUID id;
	
	private String category;
	
	private String imageUrl;

}
