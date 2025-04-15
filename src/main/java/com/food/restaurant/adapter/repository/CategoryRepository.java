package com.food.restaurant.adapter.repository;

import java.util.Optional;
import java.util.UUID;

import com.food.restaurant.core.entity.Categories;

public interface CategoryRepository {
	
	Categories save(Categories categories);
	
	Optional<Categories> findById(UUID Category_id);
}
