package com.food.restaurant.adapter.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryMenuDto {
	
	private String category;
	
	private List<FoodItemDto> foodItem;
}
