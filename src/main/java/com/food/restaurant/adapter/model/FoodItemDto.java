package com.food.restaurant.adapter.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodItemDto {
	private UUID id;
    private String name;
    private String description;
    private Integer price;
    private Double rating;
    private String imageUrl;
    private String categoryName;
    
    public FoodItemDto(String name, String description, Integer price, Double rating, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }
}
