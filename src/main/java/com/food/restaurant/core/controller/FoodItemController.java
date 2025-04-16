package com.food.restaurant.core.controller;

import java.util.ArrayList;
import java.util.List;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.restaurant.adapter.model.FoodItemResponse;
import com.food.restaurant.adapter.service.FoodItemService;
import com.food.restaurant.core.entity.FoodItem;

@RestController
@RequestMapping("/api/foodItem")
public class FoodItemController {

	@Autowired
	private FoodItemService foodItemService;

	@GetMapping("/getAllFoodItems")
	public ResponseEntity<FoodItemResponse> getAllFoodItems(){
		try {
			FoodItemResponse res = new FoodItemResponse();
			List<FoodItem> allFoodItem = foodItemService.getAllFoodItems();
			res.setFood_items(allFoodItem);
			res.setMessage("The data has been fetched successfully");
			return new ResponseEntity<FoodItemResponse>(res, HttpStatus.OK);
			
		} catch (Exception e) {
			FoodItemResponse res = new FoodItemResponse();
			res.setMessage("The error found in the getAllFoodItem service!!");
			return new ResponseEntity<FoodItemResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getAllFoodByRestaurant/{restaurant_id}")
	public ResponseEntity<FoodItemResponse> getAllFoodItemsByRestaurant(@PathVariable UUID restaurant_id){
		try {
			FoodItemResponse res = new FoodItemResponse();
			List<FoodItem> allFoodItem = foodItemService.getAllFoodItemOfRestaurant(restaurant_id);
			if(allFoodItem.isEmpty()) {
				res.setFood_items(null);
				res.setMessage("No food item found for this restaurant");
				res.setRestaurant_name(restaurant_id);
				return new ResponseEntity<FoodItemResponse>(res, HttpStatus.BAD_REQUEST);
			}
			res.setFood_items(allFoodItem);
			res.setRestaurant_name(restaurant_id);
			res.setMessage("The data has been fetched successfully");
			return new ResponseEntity<FoodItemResponse>(res, HttpStatus.OK);
			
		} catch (Exception e) {
			FoodItemResponse res = new FoodItemResponse();
			res.setMessage("The error found in the getAllFoodItemsByRestaurant service!!");
			return new ResponseEntity<FoodItemResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getallFoodItemByCategory/{category_id}")
	public ResponseEntity<FoodItemResponse> getAllFoodItemsByCategory(@PathVariable UUID category_id){
		try {
			FoodItemResponse res = new FoodItemResponse();
			List<FoodItem> allFoodItem = foodItemService.getAllFoodItemOfCategory(category_id);
			if(allFoodItem.isEmpty()) {
				res.setFood_items(null);
				res.setMessage("No food item found for this Category");
				res.setCategory_name(category_id);
				return new ResponseEntity<FoodItemResponse>(res, HttpStatus.BAD_REQUEST);
			}
			res.setFood_items(allFoodItem);
			res.setMessage("The data has been fetched successfully");
			return new ResponseEntity<FoodItemResponse>(res, HttpStatus.OK);
			
		} catch (Exception e) {
			FoodItemResponse res = new FoodItemResponse();
			res.setMessage("The error found in the getAllFoodItemsByCategory service!!");
			return new ResponseEntity<FoodItemResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/updateFoodItem")
	public ResponseEntity<FoodItemResponse> updateFoodItem(@RequestParam FoodItem foodItem){
		try {
			FoodItemResponse res = new FoodItemResponse();
			FoodItem updatedFoodItem = foodItemService.updateFoodItem(foodItem);
			if(updatedFoodItem == null) {
				res.setFood_items(null);
				res.setMessage("No food item found for this information passed in request parameter.");
				return new ResponseEntity<FoodItemResponse>(res, HttpStatus.BAD_REQUEST);
			}
			List<FoodItem> list = new ArrayList<>();
			list.add(updatedFoodItem);
			
			res.setFood_items(list);
			
			res.setMessage("The data has been updated successfully");
			return new ResponseEntity<FoodItemResponse>(res, HttpStatus.OK);
			
		} catch (Exception e) {
			FoodItemResponse res = new FoodItemResponse();
			res.setMessage("The error found in the updateFoodItem service!!");
			return new ResponseEntity<FoodItemResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getFoodItem/{FoodItemId}")
	public ResponseEntity<FoodItemResponse> getFoodItem(@PathVariable UUID FoodItemId){
		try {
			FoodItemResponse res = new FoodItemResponse();
			FoodItem result = foodItemService.getFoodItem(FoodItemId);
			if(result == null) {
				res.setFood_items(null);
				res.setMessage("No food item found for this information passed in request parameter.");
				return new ResponseEntity<FoodItemResponse>(res, HttpStatus.BAD_REQUEST);
			}
			List<FoodItem> list = new ArrayList<>();
			list.add(result);
			res.setFood_items(list);
			res.setMessage("The data has been fetched successfully");
			return new ResponseEntity<FoodItemResponse>(res, HttpStatus.OK);
			
		} catch (Exception e) {
			FoodItemResponse res = new FoodItemResponse();
			res.setMessage("The error found in the getFoodItem service!!");
			return new ResponseEntity<FoodItemResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/deleteFoodItem/{foodItemId}")
	public ResponseEntity<FoodItemResponse> deleteFoodItem(@PathVariable UUID foodItemId){
		try {
			FoodItemResponse res = new FoodItemResponse();
			foodItemService.deleteFoodItem(foodItemId);
			res.setMessage("The data has been updated successfully");
			return new ResponseEntity<FoodItemResponse>(res, HttpStatus.OK);
			
		} catch (Exception e) {
			FoodItemResponse res = new FoodItemResponse();
			res.setMessage("The error found in the deleteFoodItem service!!");
			return new ResponseEntity<FoodItemResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
