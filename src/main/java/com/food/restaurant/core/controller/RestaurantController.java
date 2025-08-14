package com.food.restaurant.core.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.food.restaurant.adapter.constant.AppConstant;
import com.food.restaurant.adapter.mapper.FoodItemMapper;
import com.food.restaurant.adapter.mapper.RestaurantMapper;
import com.food.restaurant.adapter.model.AddFoodItemDto;
import com.food.restaurant.adapter.model.AddFoodItemRequest;
import com.food.restaurant.adapter.model.AddFoodItemResponse;
import com.food.restaurant.adapter.model.AllRestaurantResponse;
import com.food.restaurant.adapter.model.CategoryMenuDto;
import com.food.restaurant.adapter.model.CategoryMenuResponse;
import com.food.restaurant.adapter.model.FoodItemDto;
import com.food.restaurant.adapter.model.GenericResponse;
import com.food.restaurant.adapter.model.RestaurantDto;
import com.food.restaurant.adapter.model.StatusCode;
import com.food.restaurant.adapter.model.SubmitRestaurantRatingRequest;
import com.food.restaurant.adapter.model.UpdateFoodItemsByRestaurantRequest;
import com.food.restaurant.adapter.service.RestaurantService;
import com.food.restaurant.core.entity.FoodItem;
import com.food.restaurant.core.entity.Restaurant;
import com.food.restaurant.core.entity.RestaurantReviews;


@RestController
@RequestMapping(AppConstant.RESTAURANT_CONTROLLER)
public class RestaurantController {
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private RestaurantMapper restaurantMapper;
	
	
	@PostMapping(AppConstant.CREATE_RESTAURANT)
	public GenericResponse<?> createRestaurant(@RequestBody RestaurantDto restaurant){
		try {
			
			Restaurant res = restaurantMapper.dtoToEntity(restaurant);
			Restaurant restaurant1 = restaurantService.createRestaurant(res);
			if(restaurant1 == null) {
				return new GenericResponse<>("Restaurant not created...", StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			
			return new GenericResponse<>("Restaurant created successfully!!", StatusCode.of(HttpStatus.OK), restaurant1);
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong.....", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PostMapping(AppConstant.ADD_FOOD_ITEMS)
	public GenericResponse<AddFoodItemResponse> addFoodItem(@RequestBody FoodItemDto addFoodItemDto){
		try {
			
			return new GenericResponse<>("Food item added successfully!!",  StatusCode.of(HttpStatus.OK), restaurantService.addFoodItems(addFoodItemDto));
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Internal Server Error", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PutMapping(AppConstant.UPDATE_RESTAURANT)
	public GenericResponse<Restaurant> updateRestaurant(@RequestBody RestaurantDto restaurant){
		try {
			Restaurant res = restaurantMapper.dtoToEntity(restaurant);
			Restaurant result = restaurantService.updateRestaurant(res);
			return new GenericResponse<>("Restaurant Details updated successfully!!",StatusCode.of(HttpStatus.OK), result);
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("The error in updating the restaurant details", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@DeleteMapping(AppConstant.DELETE_RESTAURANT)
	public GenericResponse<?> deleteRestaurant(@RequestParam UUID restaurant_id){
		try {
			restaurantService.deleteRestaurant(restaurant_id);
			return new GenericResponse<>("The restaurant has been deleted successfully!", StatusCode.of(HttpStatus.OK), null);
			
		} catch (Exception e) {
			return new GenericResponse<>("There has something went wrong ion deleting the restaurant!!", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR),null);
		}
	}
	
	@GetMapping(AppConstant.GET_ALL_RESTAURANT)
	public GenericResponse<AllRestaurantResponse> getAllRestaurant(){
		try {
			List<Restaurant> allRestaurants = restaurantService.getAllRestaurant();
			AllRestaurantResponse res = new AllRestaurantResponse();
			res.setAllRestaurant(allRestaurants);
			
			
			return new GenericResponse<>("The restaurant has been fetch succesfully",StatusCode.of(HttpStatus.OK), res);
			
		} catch (Exception e) {
			return new GenericResponse<AllRestaurantResponse>("Error in getAllRestaurant service", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@GetMapping(AppConstant.GET_MENU_FOR_RESTAURANT)
	public GenericResponse<CategoryMenuResponse> getMenuForRestaurant(@PathVariable UUID restaurant_id){
		try {
			CategoryMenuResponse res = new CategoryMenuResponse();
			List<CategoryMenuDto> result = restaurantService.getMenuOfRestaurant(restaurant_id);
			res.setMenu(result);
			return new GenericResponse<>("The menu for the perticular restaurant has been fetched successfully",StatusCode.of(HttpStatus.OK), res);
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("The error occur while executing the getMenuForResturant",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PatchMapping(AppConstant.UPDATE_ACTIVE_NOW)
	public GenericResponse<Boolean> updateActiveNow(@RequestParam UUID restaurant_id){
		try {
			Boolean updateData = restaurantService.makeCloseOrOpenRestaurant(restaurant_id);
			
			if(!updateData) {
				return new GenericResponse<>("The restaurant availability is not updated", StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			
			return new GenericResponse<>("The restaurant availability is updated successfully!!", StatusCode.of(HttpStatus.OK), updateData);
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("The error occur while executing the updateActiveNowStatus",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PatchMapping(AppConstant.UPDATE_FOOD_ITEMS)
	public GenericResponse<Boolean> updateFoodItems(@RequestBody UpdateFoodItemsByRestaurantRequest request){
		try {
			Boolean updateFoodItems = restaurantService.updateFoodItems(request.getFoodItems());
			
			if(!updateFoodItems) {
				return new GenericResponse<>("Can't update food items", StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			
			return new GenericResponse<>("The food items is updated successfully!!", StatusCode.of(HttpStatus.OK), updateFoodItems);
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("The error occur while executing the updateFoodItems",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PutMapping(AppConstant.SUBMIT_RESTAURANT_RATING)
	public GenericResponse<RestaurantReviews> submitReview(@RequestBody SubmitRestaurantRatingRequest request){
		try {
			RestaurantReviews review = restaurantService.submitRating(request);
			
			if(review == null) {
				return new GenericResponse<>("Can't able to submit restaurant rating!!", StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			
			return new GenericResponse<>("Rating submitted successfully!!", StatusCode.of(HttpStatus.OK), review);
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("The error occur while executing the submitRating",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
}
