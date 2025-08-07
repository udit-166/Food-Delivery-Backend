package com.food.restaurant.adapter.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.restaurant.adapter.mapper.CategoryMapper;
import com.food.restaurant.adapter.model.UpdateCategoryRequestDto;
import com.food.restaurant.adapter.service.CategoriesService;
import com.food.restaurant.core.entity.Categories;
import com.food.restaurant.core.usecase.CategoriesUsecase;

@Service
public class CategoriesServiceImpl implements CategoriesService{
	
	@Autowired
	private CategoriesUsecase categoriesUsecase;
	
	@Autowired
	private CategoryMapper categoryMapper;

	@Override
	public Categories newCategories(Categories categories) {
		
		return categoriesUsecase.newCategories(categories);
	}

	@Override
	public Categories updateCategories(UpdateCategoryRequestDto categories) {
		
		Categories request = categoryMapper.dtoToEntity(categories);
		return categoriesUsecase.updateCategories(request);
	}

}
