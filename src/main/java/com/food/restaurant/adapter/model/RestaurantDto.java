package com.food.restaurant.adapter.model;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class RestaurantDto {

	private UUID id;
    private String name;
    private String imageUrl;
    private Double averageRating;
    private Integer totalRating;
    private List<FoodItemDto> foodItems;
}
