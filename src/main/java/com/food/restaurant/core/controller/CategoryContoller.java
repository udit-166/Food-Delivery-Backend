package com.food.restaurant.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.restaurant.adapter.constant.AppConstant;
import com.food.restaurant.adapter.model.CategoryResponse;
import com.food.restaurant.adapter.model.GenericResponse;
import com.food.restaurant.adapter.model.StatusCode;
import com.food.restaurant.adapter.model.UpdateCategoryRequestDto;
import com.food.restaurant.adapter.service.CategoriesService;
import com.food.restaurant.core.entity.Categories;

@RestController
@RequestMapping(AppConstant.CATEGORY_CONTROLLER)
public class CategoryContoller {

	@Autowired
	private CategoriesService categoriesService;
	
	@PostMapping(AppConstant.NEW_CATEGORY)
	public GenericResponse<CategoryResponse> createNewCategory(@RequestBody Categories categories){
		try {
			CategoryResponse res = new CategoryResponse();
			Categories category = categoriesService.newCategories(categories);
			if(category == null) {
				
				return new GenericResponse<>("Can not create new categories!!", StatusCode.of(HttpStatus.CONFLICT), null);
			}
			
			res.setCategory(category);
			res.setId(category.getId());
			return new GenericResponse<>("The new category has been created successfully!!", StatusCode.of(HttpStatus.OK), res);
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PutMapping(AppConstant.UPDATE_CATEGORY)
	public GenericResponse<CategoryResponse> updateCategories(@RequestBody UpdateCategoryRequestDto categories){
		try {
			CategoryResponse res = new CategoryResponse();
			Categories category = categoriesService.updateCategories(categories);
			if(category == null) {
				
				return new GenericResponse<>("Something went wrong", StatusCode.of(HttpStatus.CONFLICT), null);
			}
			
			res.setCategory(category);
			res.setId(category.getId());
			return new GenericResponse<>("The category has been updated successfully!!",StatusCode.of(HttpStatus.OK), res);
		} catch (Exception e) {
			CategoryResponse res = new CategoryResponse();
			res.setCategory(null);
			return new GenericResponse<>("Something went wrong",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
}
