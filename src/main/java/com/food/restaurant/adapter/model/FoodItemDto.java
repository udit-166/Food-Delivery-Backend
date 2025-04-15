package com.food.restaurant.adapter.model;

import java.util.UUID;

import lombok.Data;

@Data
public class FoodItemDto {
	private UUID id;
    private String name;
    private String description;
    private Integer price;
    private Double rating;
    private String imageUrl;
    private String categoryName;
}
