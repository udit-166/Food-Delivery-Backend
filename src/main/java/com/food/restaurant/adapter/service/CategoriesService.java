package com.food.restaurant.adapter.service;

import com.food.restaurant.adapter.model.UpdateCategoryRequestDto;
import com.food.restaurant.core.entity.Categories;

public interface CategoriesService {

	public Categories newCategories(Categories categories);
	
	public Categories updateCategories(UpdateCategoryRequestDto categories);
}
