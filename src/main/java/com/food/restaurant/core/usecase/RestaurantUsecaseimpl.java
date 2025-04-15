package com.food.restaurant.core.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.food.restaurant.adapter.model.AddFoodItemDto;
import com.food.restaurant.adapter.model.AddFoodItemResponse;
import com.food.restaurant.adapter.repository.FoodItemRepositories;
import com.food.restaurant.adapter.repository.RestaurantRepositories;
import com.food.restaurant.core.entity.FoodItem;
import com.food.restaurant.core.entity.Restaurant;

@Service
public class RestaurantUsecaseimpl implements RestaurantUsecase{
	
	private RestaurantRepositories restaurantRepositories;
	
	private FoodItemRepositories foodItemRepositories;
	
	public RestaurantUsecaseimpl() {
		
	}

	@Override
	public AddFoodItemResponse addFoodItems(AddFoodItemDto foodItem) {
		Restaurant restaurant = restaurantRepositories.findByName(foodItem.getRestaurant_name());
        if(restaurant == null){
            // Create new restaurant
            Restaurant newRestaurant = new Restaurant();
            newRestaurant.setName(foodItem.getRestaurant_name());
            newRestaurant.setImage_url(foodItem.getImageUrl());
            newRestaurant.setAverage_rating(foodItem.getRating());
            newRestaurant.setTotal_rating(1);
            restaurantRepositories.save(newRestaurant);
        };

		    // If restaurant exists, update rating
		    if (restaurant.getId() != null) {
		        double totalRating = restaurant.getAverage_rating() * restaurant.getTotal_rating();
		        int newTotalRatings = restaurant.getTotal_rating() + 1;
		        double newAverageRating = (totalRating + foodItem.getRating()) / newTotalRatings;
		
		        restaurant.setAverage_rating(newAverageRating);
		        restaurant.setTotal_rating(newTotalRatings);
		        restaurantRepositories.save(restaurant);
		    }
		
		    // Add food item
		    FoodItem foodItem1 = new FoodItem();
		    foodItem1.setName(foodItem.getName());
		    foodItem1.setCategory(foodItem.getCategory());
		    foodItem1.setDescription(foodItem.getDescription());
		    foodItem1.setPrice(foodItem.getPrice());
		    foodItem1.setRating(foodItem.getRating());
		    foodItem1.setImageUrl(foodItem.getImageUrl());
		    foodItem1.setRestaurant(restaurant);
		
		    foodItemRepositories.save(foodItem1);
		    
		    //setting the data in response data format
		    
		    AddFoodItemResponse response = new AddFoodItemResponse();
		    
		    response.setAddFoodItemDto(foodItem);
		    response.setMessage("Add Food Item successfully !!");
		    
		    return response;
		}

	@Override
	public List<Restaurant> getAllRestaurant() {
		
		return restaurantRepositories.getAllRestaurant();
	}

	@Override
	public List<FoodItem> getAllFoodItems() {
		return foodItemRepositories.getAllFoodItems();
	}

	@Override
	public Restaurant updateRestaurant(Restaurant restaurant) {
		if(restaurant == null) {
			return null;
		}
		Restaurant updateRestaurant = restaurantRepositories.findByName(restaurant.getName());
		
		updateRestaurant.setAverage_rating(restaurant.getAverage_rating());
		updateRestaurant.setImage_url(restaurant.getImage_url());
		updateRestaurant.setClosing_time(restaurant.getClosing_time());
		updateRestaurant.setCustomer_care_number(restaurant.getCustomer_care_number());
		updateRestaurant.setIs_active(restaurant.getIs_active());
		updateRestaurant.setName(restaurant.getName());
		updateRestaurant.setOpening_time(restaurant.getOpening_time());
		updateRestaurant.setRestaurant_email(restaurant.getRestaurant_email());
		updateRestaurant.setTotal_rating(restaurant.getTotal_rating());
		
		return restaurantRepositories.save(updateRestaurant);
	}

	@Override
	public void deleteRestaurant(String restaurant_name) {
		Restaurant deleteRestaurant = restaurantRepositories.findByName(restaurant_name);
		
		deleteRestaurant.setIs_active(false);
		
		restaurantRepositories.save(deleteRestaurant);
		
	}

}
