package com.food.restaurant.core.usecase;

import com.food.restaurant.core.entity.Categories;

public interface CategoriesUsecase {

	public Categories newCategories(Categories categories);
	
	public Categories updateCategories(Categories categories);
}
