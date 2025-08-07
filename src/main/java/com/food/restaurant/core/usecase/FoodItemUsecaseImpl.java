package com.food.restaurant.core.usecase;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.restaurant.adapter.model.FcmNotification;
import com.food.restaurant.adapter.repository.FoodItemRepositories;
import com.food.restaurant.core.entity.FoodItem;

@Service
public class FoodItemUsecaseImpl implements FoodItemUsecase{
	
	@Autowired
	private FoodItemRepositories foodItemRepositories;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private KafkaTemplate<String, FcmNotification> kafkaTemplate;

	@Override
	public List<FoodItem> getAllFoodItems() {
		List<FoodItem> result = foodItemRepositories.getAllFoodItems();
		
		return result;
	}

	@Override
	public List<FoodItem> getAllFoodItemOfRestaurant(UUID restaurantId) {
	    String key = "restaurant_foods_" + restaurantId;

	    // Check Redis
	    String cachedData = stringRedisTemplate.opsForValue().get(key);
	    if (cachedData != null) {
	        try {
	            return objectMapper.readValue(cachedData, new TypeReference<List<FoodItem>>() {});
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }
	    }

	    // DB call
	    List<FoodItem> result = foodItemRepositories.findAllByRestaurant_Id(restaurantId);

	    // Store in Redis
	    try {
	        String json = objectMapper.writeValueAsString(result);
	        stringRedisTemplate.opsForValue().set(key, json, Duration.ofMinutes(15));
	    } catch (JsonProcessingException e) {
	        e.printStackTrace();
	    }

	    return result;
	}


	@Override
	public List<FoodItem> getAllFoodItemOfCategory(UUID categoryId) {
	    String key = "category_foods_" + categoryId;

	    // Check Redis
	    String cachedData = stringRedisTemplate.opsForValue().get(key);
	    if (cachedData != null) {
	        try {
	            return objectMapper.readValue(cachedData, new TypeReference<List<FoodItem>>() {});
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }
	    }

	    // DB call
	    List<FoodItem> result = foodItemRepositories.findAllByCategory_Id(categoryId);

	    // Store in Redis
	    try {
	        String json = objectMapper.writeValueAsString(result);
	        stringRedisTemplate.opsForValue().set(key, json, Duration.ofMinutes(15));
	    } catch (JsonProcessingException e) {
	        e.printStackTrace();
	    }

	    return result;
	}


	@Override
	public FoodItem getFoodItem(UUID food_item_id) {
		
		FoodItem result = foodItemRepositories.findById(food_item_id);
		return result;
	}

	@Override
	public FoodItem updateFoodItem(FoodItem foodItem) {
		FoodItem toUpdate = foodItemRepositories.findById(foodItem.getId());
		if(toUpdate==null) {
			return null;
		}
		toUpdate.setCategory(foodItem.getCategory());
		toUpdate.setDescription(foodItem.getDescription());
		toUpdate.setImageUrl(foodItem.getImageUrl());
		toUpdate.setName(foodItem.getName());
		toUpdate.setPrice(foodItem.getPrice());
		toUpdate.setRating(foodItem.getRating());
		
		foodItemRepositories.save(toUpdate);
		
		FcmNotification notification = new FcmNotification();
		notification.setTo("");
		notification.setTitle("");
		notification.setBody(null);
		
		kafkaTemplate.send("send-notification", notification);
		return toUpdate;
	}

	@Override
	public void deleteFoodItem(UUID food_item_id) {
		FoodItem food = foodItemRepositories.findById(food_item_id);
		
		food.setIs_active(false);
		foodItemRepositories.save(food);
		
	}

}
