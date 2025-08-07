package com.food.restaurant.core.usecase;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.restaurant.adapter.repository.CategoryRepository;
import com.food.restaurant.core.entity.Categories;

@Service
public class CategoriesUsecaseImpl implements CategoriesUsecase{
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Categories newCategories(Categories categories) {
		
		if(categories == null) {
			return null;
		}
		Categories newCategories = categoryRepository.save(categories);
		return newCategories;
	}

	@Override
	public Categories updateCategories(Categories categories) {
		Optional<Categories> category = categoryRepository.findById(categories.getId());
		
		if(category.isEmpty()) {
			return null;
		}
		Categories toUpdate = category.get();
		
		toUpdate.setCategory(categories.getCategory());
		toUpdate.setImageUrl(categories.getImageUrl());
		return categoryRepository.save(toUpdate);
	}

}
