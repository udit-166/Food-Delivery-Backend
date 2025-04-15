package com.food.restaurant.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.restaurant.adapter.model.CategoryResponse;
import com.food.restaurant.adapter.service.CategoriesService;
import com.food.restaurant.core.entity.Categories;

@RestController
@RequestMapping("/api/category")
public class CategoryContoller {

	@Autowired
	private CategoriesService categoriesService;
	
	@PostMapping("/newCategory")
	public ResponseEntity<CategoryResponse> createNewCategory(@RequestParam Categories categories){
		try {
			CategoryResponse res = new CategoryResponse();
			Categories category = categoriesService.newCategories(categories);
			if(category == null) {
				res.setCategory(null);
				res.setId(null);
				res.setMessage("Something went wrong");
				
				return new ResponseEntity<>(res, HttpStatus.CONFLICT);
			}
			
			res.setCategory(category.getCategory());
			res.setId(category.getId());
			res.setMessage("The new category has been created successfully!!");
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			CategoryResponse res = new CategoryResponse();
			res.setCategory(null);
			res.setId(null);
			res.setMessage("Internal Server Error");
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/updateCategory")
	public ResponseEntity<CategoryResponse> updateCategories(@RequestParam Categories categories){
		try {
			CategoryResponse res = new CategoryResponse();
			Categories category = categoriesService.updateCategories(categories);
			if(category == null) {
				res.setCategory(null);
				res.setId(null);
				res.setMessage("Something went wrong");
				
				return new ResponseEntity<>(res, HttpStatus.CONFLICT);
			}
			
			res.setCategory(category.getCategory());
			res.setId(category.getId());
			res.setMessage("The category has been updated successfully!!");
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			CategoryResponse res = new CategoryResponse();
			res.setCategory(null);
			res.setId(null);
			res.setMessage("Internal Server Error");
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
