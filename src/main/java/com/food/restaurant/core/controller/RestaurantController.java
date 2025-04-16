package com.food.restaurant.core.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.food.restaurant.adapter.model.AddFoodItemResponse;
import com.food.restaurant.adapter.model.AllRestaurantResponse;
import com.food.restaurant.adapter.model.CategoryMenuDto;
import com.food.restaurant.adapter.model.CategoryMenuResponse;
import com.food.restaurant.adapter.service.RestaurantService;
import com.food.restaurant.core.entity.FoodItem;
import com.food.restaurant.core.entity.Restaurant;


@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
	
	@Autowired
	private RestaurantService restaurantService;
	
	@PostMapping("/addFoodItems")
	public ResponseEntity<AddFoodItemResponse> addFoodItem(@RequestParam FoodItem addFoodItemDto){
		try {
			return new ResponseEntity<>(restaurantService.addFoodItems(addFoodItemDto), HttpStatus.OK);
		} catch (Exception e) {
			AddFoodItemResponse response = new AddFoodItemResponse();
			response.setAddFoodItemDto(null);
			response.setMessage("Internal Server Error");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/updateRestaurant")
	public ResponseEntity<?> updateRestaurant(@RequestParam Restaurant restaurant){
		try {
			return new ResponseEntity<>(restaurantService.updateRestaurant(restaurant), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("The error in updating the restaurant details", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/deleteRestaurant")
	public ResponseEntity<?> deleteRestaurant(@RequestParam String Restaurant_name){
		try {
			restaurantService.deleteRestaurant(Restaurant_name);
			return new ResponseEntity<>("The restaurant has been deleted successfully!", HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>("There has something went wrong ion deleting the restaurant!!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getAllRestaurant")
	public ResponseEntity<AllRestaurantResponse> getAllRestaurant(){
		try {
			List<Restaurant> allRestaurants = restaurantService.getAllRestaurant();
			AllRestaurantResponse res = new AllRestaurantResponse();
			res.setAllRestaurant(allRestaurants);
			res.setMessage("The restaurant has been fetch succesfully");
			
			
			return new ResponseEntity<AllRestaurantResponse>(res, HttpStatus.OK);
			
		} catch (Exception e) {
			AllRestaurantResponse res = new AllRestaurantResponse();
			res.setAllRestaurant(null);
			res.setMessage("Error in getAllRestaurant service");
			return new ResponseEntity<AllRestaurantResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getMenuForRestaurant/{restaurant_id}")
	public ResponseEntity<CategoryMenuResponse> getMenuForRestaurant(@PathVariable UUID restaurant_id){
		try {
			CategoryMenuResponse res = new CategoryMenuResponse();
			List<CategoryMenuDto> result = restaurantService.getMenuOfRestaurant(restaurant_id);
			res.setMenu(result);
			res.setMessage("The menu for the perticular restaurant has been fetched successfully");
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			CategoryMenuResponse res = new CategoryMenuResponse();
			res.setMessage("The error occur while executing the getMenuForResturant");
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
