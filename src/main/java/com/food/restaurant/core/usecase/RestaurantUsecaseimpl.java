package com.food.restaurant.core.usecase;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.restaurant.adapter.constant.AppConstant;
import com.food.restaurant.adapter.model.AddFoodItemDto;
import com.food.restaurant.adapter.model.AddFoodItemResponse;
import com.food.restaurant.adapter.model.CategoryMenuDto;
import com.food.restaurant.adapter.model.CategoryMenuResponse;
import com.food.restaurant.adapter.model.FcmNotification;
import com.food.restaurant.adapter.model.FoodItemDto;
import com.food.restaurant.adapter.repository.CategoryRepository;
import com.food.restaurant.adapter.repository.FoodItemRepositories;
import com.food.restaurant.adapter.repository.RestaurantRepositories;
import com.food.restaurant.core.entity.Categories;
import com.food.restaurant.core.entity.FoodItem;
import com.food.restaurant.core.entity.Restaurant;

@Service
public class RestaurantUsecaseimpl implements RestaurantUsecase{
	
	private RestaurantRepositories restaurantRepositories;
	
	private FoodItemRepositories foodItemRepositories;
	
	private CategoryRepository categoryRepository;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private KafkaTemplate<String, FcmNotification> kafkaTemplate;
	
	public RestaurantUsecaseimpl() {
		
	}

	@Override
	public AddFoodItemResponse addFoodItems(FoodItem foodItem) {
		if (foodItem.getRestaurant() == null || foodItem.getRestaurant().getId() == null) {
		    AddFoodItemResponse failedResponse = new AddFoodItemResponse();
		    failedResponse.setMessage("Restaurant ID is missing or null. Food item not added.");
		    return failedResponse;
		}

		// Validate category_id
		if (foodItem.getCategory() == null || foodItem.getCategory().getId() == null) {
		    AddFoodItemResponse failedResponse = new AddFoodItemResponse();
		    failedResponse.setMessage("Category ID is missing or null. Food item not added.");
		    return failedResponse;
		}

		// Fetch restaurant and category from DB
		Optional<Restaurant> optionalRestaurant = restaurantRepositories.findById(foodItem.getRestaurant().getId());
		Optional<Categories> optionalCategory = categoryRepository.findById(foodItem.getCategory().getId());

		if (optionalRestaurant.isEmpty() || optionalCategory.isEmpty()) {
		    AddFoodItemResponse failedResponse = new AddFoodItemResponse();
		    failedResponse.setMessage("Restaurant or Category not found in database. Food item not added.");
		    return failedResponse;
		}

		// Add food item
		FoodItem foodItem1 = new FoodItem();
		foodItem1.setName(foodItem.getName());
		foodItem1.setDescription(foodItem.getDescription());
		foodItem1.setPrice(foodItem.getPrice());
		foodItem1.setRating(foodItem.getRating());
		foodItem1.setImageUrl(foodItem.getImageUrl());
		foodItem1.setRestaurant(optionalRestaurant.get());
		foodItem1.setCategory(optionalCategory.get());

		foodItemRepositories.save(foodItem1);

		// Prepare response
		AddFoodItemResponse response = new AddFoodItemResponse();
		response.setMessage("Food item added successfully!");
		FcmNotification notification = new FcmNotification();
		
		notification.setTo("");
		notification.setTitle("");
		notification.setBody(null);
		
		kafkaTemplate.send("send-notification", notification);
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
		
		FcmNotification notification = new FcmNotification();
		
		notification.setTo("");
		notification.setTitle("");
		notification.setBody(null);
		
		kafkaTemplate.send("send-notification", notification);
		
		return restaurantRepositories.save(updateRestaurant);
	}

	@Override
	public void deleteRestaurant(String restaurant_name) {
		Restaurant deleteRestaurant = restaurantRepositories.findByName(restaurant_name);
		
		deleteRestaurant.setIs_active(false);
		
		restaurantRepositories.save(deleteRestaurant);
		
	}

	@Override
	public List<CategoryMenuDto> getMenuOfRestaurant(UUID restaurant_id) {
		String cacheKey = AppConstant.MENU_CACHE_PREFIX + restaurant_id.toString();
	    String cachedJson = stringRedisTemplate.opsForValue().get(cacheKey);
	    if (cachedJson != null) {
	        try {
	            return objectMapper.readValue(
	                cachedJson,
	                new TypeReference<List<CategoryMenuDto>>() {}
	            );
	        } catch (JsonProcessingException e) {
	            throw new RuntimeException("Failed to parse cached menu JSON", e);
	        }
	    }
	    List<FoodItem> foodItems = foodItemRepositories.findAllByRestaurant_Id(restaurant_id);

	    Map<String, List<FoodItemDto>> grouped = foodItems.stream()
	    	    .collect(Collectors.groupingBy(
	    	        item -> item.getCategory().getCategory(),
	    	        Collectors.mapping(item -> new FoodItemDto(
	    	            item.getName(),
	    	            item.getDescription(),
	    	            item.getPrice(),
	    	            item.getRating(),
	    	            item.getImageUrl()
	    	        ), Collectors.toList())
	    	    ));

	    List<CategoryMenuDto> menu = grouped.entrySet().stream()
	        .map(entry -> new CategoryMenuDto(entry.getKey(), entry.getValue()))
	        .collect(Collectors.toList());
	    try {
	        String jsonMenu = objectMapper.writeValueAsString(menu);
	        stringRedisTemplate.opsForValue().set(cacheKey, jsonMenu, Duration.ofMinutes(15));
	    } catch (JsonProcessingException e) {
	        throw new RuntimeException("Failed to serialize menu JSON", e);
	    }

	    return menu;
	}


}
