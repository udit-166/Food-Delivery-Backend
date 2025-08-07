package com.food.restaurant.adapter.model;

import java.util.UUID;

import lombok.Data;

@Data
public class AddFoodItemRequest {
	private String name;
    private String description;
    private Integer price;
    private Double rating;
    private String imageUrl;
    private Boolean is_active;
    private UUID categoryId;
    private UUID restaurantId;
}
