package com.food.restaurant.adapter.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.food.restaurant.core.entity.Categories;
import com.food.restaurant.core.repository.CategoriesReposiotory;

public class CategoryRepositoryImpl implements CategoryRepository{
	
	@Autowired
	private CategoriesReposiotory categoriesReposiotory;

	@Override
	public Categories save(Categories categories) {
		return categoriesReposiotory.save(categories);
	}

	@Override
	public Optional<Categories> findById(UUID Category_id) {
		return categoriesReposiotory.findById(Category_id);
	}

}
