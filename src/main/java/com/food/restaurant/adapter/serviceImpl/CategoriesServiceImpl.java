package com.food.restaurant.adapter.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.restaurant.adapter.service.CategoriesService;
import com.food.restaurant.core.entity.Categories;
import com.food.restaurant.core.usecase.CategoriesUsecase;

@Service
public class CategoriesServiceImpl implements CategoriesService{
	
	@Autowired
	private CategoriesUsecase categoriesUsecase;

	@Override
	public Categories newCategories(Categories categories) {
		
		return categoriesUsecase.newCategories(categories);
	}

	@Override
	public Categories updateCategories(Categories categories) {
		return categoriesUsecase.updateCategories(categories);
	}

}
