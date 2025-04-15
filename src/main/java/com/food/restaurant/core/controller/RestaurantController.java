package com.food.restaurant.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.restaurant.adapter.model.AddFoodItemDto;
import com.food.restaurant.adapter.model.AddFoodItemResponse;
import com.food.restaurant.adapter.service.RestaurantService;
import com.food.restaurant.core.entity.Restaurant;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
	
	@Autowired
	private RestaurantService restaurantService;
	
	@PostMapping("/addFoodItems")
	public ResponseEntity<AddFoodItemResponse> addFoodItem(@RequestParam AddFoodItemDto addFoodItemDto){
		try {
			return new ResponseEntity<>(restaurantService.addFoodItems(addFoodItemDto), HttpStatus.OK);
		} catch (Exception e) {
			AddFoodItemResponse response = new AddFoodItemResponse();
			response.setAddFoodItemDto(null);
			response.setMessage("Internal Server Error");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	public ResponseEntity<?> updateRestaurant(@RequestParam Restaurant restaurant){
//		try {
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
}
