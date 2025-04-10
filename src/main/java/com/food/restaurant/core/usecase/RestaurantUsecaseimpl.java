package com.food.restaurant.core.usecase;

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
            newRestaurant.setImageUrl(foodItem.getImageUrl());
            newRestaurant.setAverageRating(foodItem.getRating());
            newRestaurant.setTotalRating(1);
            restaurantRepositories.save(newRestaurant);
        };

		    // If restaurant exists, update rating
		    if (restaurant.getId() != null) {
		        double totalRating = restaurant.getAverageRating() * restaurant.getTotalRating();
		        int newTotalRatings = restaurant.getTotalRating() + 1;
		        double newAverageRating = (totalRating + foodItem.getRating()) / newTotalRatings;
		
		        restaurant.setAverageRating(newAverageRating);
		        restaurant.setTotalRating(newTotalRatings);
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

}
