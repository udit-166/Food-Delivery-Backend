package com.food.restaurant.core.controller;

import java.util.ArrayList;
import java.util.List;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.restaurant.adapter.constant.AppConstant;
import com.food.restaurant.adapter.model.FoodItemDto;
import com.food.restaurant.adapter.model.FoodItemResponse;
import com.food.restaurant.adapter.model.GenericResponse;
import com.food.restaurant.adapter.model.StatusCode;
import com.food.restaurant.adapter.service.FoodItemService;
import com.food.restaurant.core.entity.FoodItem;

@RestController
@RequestMapping(AppConstant.FOOD_CONTROLLER)
public class FoodItemController {

	@Autowired
	private FoodItemService foodItemService;

	@GetMapping(AppConstant.GET_ALL_FOOD_ITEMS)
	public GenericResponse<FoodItemResponse> getAllFoodItems(){
		try {
			FoodItemResponse res = new FoodItemResponse();
			List<FoodItem> allFoodItem = foodItemService.getAllFoodItems();
			res.setFood_items(allFoodItem);
			return new GenericResponse<>("The data has been fetched successfully", StatusCode.of(HttpStatus.OK), res);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("The error found in the getAllFoodItem service!!", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@GetMapping(AppConstant.GET_ALL_FOOD_BY_RESTAURANT)
	public GenericResponse<FoodItemResponse> getAllFoodItemsByRestaurant(@PathVariable UUID restaurant_id){
		try {
			FoodItemResponse res = new FoodItemResponse();
			List<FoodItem> allFoodItem = foodItemService.getAllFoodItemOfRestaurant(restaurant_id);
			if(allFoodItem.isEmpty()) {
				return new GenericResponse<>("No food item found for this restaurant", StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			res.setFood_items(allFoodItem);
			res.setRestaurant_name(restaurant_id);
			return new GenericResponse<>("The data has been fetched successfully", StatusCode.of(HttpStatus.OK), res);
			
		} catch (Exception e) {
			return new GenericResponse<>("The error found in the getAllFoodItemsByRestaurant service!!", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@GetMapping(AppConstant.GET_ALL_FOOD_ITEM_BY_CATEGORY)
	public GenericResponse<FoodItemResponse> getAllFoodItemsByCategory(@PathVariable UUID category_id){
		try {
			FoodItemResponse res = new FoodItemResponse();
			List<FoodItem> allFoodItem = foodItemService.getAllFoodItemOfCategory(category_id);
			if(allFoodItem.isEmpty()) {
				return new GenericResponse<>("No food item found for this Category",StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			res.setFood_items(allFoodItem);
			return new GenericResponse<>("The data has been fetched successfully", StatusCode.of(HttpStatus.OK), res);
			
		} catch (Exception e) {
			return new GenericResponse<>("The error found in the getAllFoodItemsByCategory service!!", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PutMapping(AppConstant.UPDATE_FOOD_ITEM)
	public GenericResponse<FoodItemResponse> updateFoodItem(@RequestBody FoodItemDto foodItem){
		try {
			FoodItemResponse res = new FoodItemResponse();
			FoodItem updatedFoodItem = foodItemService.updateFoodItem(foodItem);
			if(updatedFoodItem == null) {
				return new GenericResponse<>("No food item found for this information passed in request parameter.",StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			List<FoodItem> list = new ArrayList<>();
			list.add(updatedFoodItem);
			
			res.setFood_items(list);
			
			return new GenericResponse<>("The data has been updated successfully", StatusCode.of(HttpStatus.OK), res);
			
		} catch (Exception e) {
			return new GenericResponse<>("The error found in the updateFoodItem service!!", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@GetMapping(AppConstant.GET_FOOD_ITEM)
	public GenericResponse<FoodItemResponse> getFoodItem(@PathVariable UUID FoodItemId){
		try {
			FoodItemResponse res = new FoodItemResponse();
			FoodItem result = foodItemService.getFoodItem(FoodItemId);
			if(result == null) {
				return new GenericResponse<>("No food item found for this information passed in request parameter.", StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			List<FoodItem> list = new ArrayList<>();
			list.add(result);
			res.setFood_items(list);
			return new GenericResponse<>("The data has been fetched successfully!!",StatusCode.of(HttpStatus.OK), res);
			
		} catch (Exception e) {
			return new GenericResponse<>("The error found in the getFoodItem service!!", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@DeleteMapping(AppConstant.DELETE_FOOD_ITEM)
	public GenericResponse<FoodItemResponse> deleteFoodItem(@PathVariable UUID foodItemId){
		try {
			foodItemService.deleteFoodItem(foodItemId);
			return new GenericResponse<>("The data has been updated successfully",StatusCode.of(HttpStatus.OK), null);
			
		} catch (Exception e) {
			return new GenericResponse<>("The error found in the deleteFoodItem service!!", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
}
