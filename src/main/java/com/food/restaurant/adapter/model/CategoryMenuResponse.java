package com.food.restaurant.adapter.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryMenuResponse {

	private List<CategoryMenuDto> menu;
	
	private String message;
}
