package com.food.restaurant.adapter.constant;

public class AppConstant {

	public static final String RESTAURANT_ENTITY = "restaurant";
	
	public static final String FOOD_ITEM_ENTITY = "food_item";
	
	public static final String CATEGORIES_ENTITY = "categories";
	
	public static final String MENU_CACHE_PREFIX = "resturant:";
	
	public static final String FOOD_CONTROLLER = "/api/food";
	
	public static final String GET_ALL_FOOD_ITEMS = "/getAllFoodItems";
	
	public static final String GET_ALL_FOOD_BY_RESTAURANT = "/getAllFoodByRestaurant/{restaurant_id}";
	
	public static final String GET_ALL_FOOD_ITEM_BY_CATEGORY = "/getallFoodItemByCategory/{category_id}";
	
	public static final String UPDATE_FOOD_ITEM = "/updateFoodItem";
	
	public static final String GET_FOOD_ITEM = "/getFoodItem/{FoodItemId}";
	
	public static final String DELETE_FOOD_ITEM = "/deleteFoodItem/{foodItemId}";
	
	public static final String RESTAURANT_CONTROLLER = "/api/restaurant";
	
	public static final String CREATE_RESTAURANT = "/createRestauran";
	
	public static final String ADD_FOOD_ITEMS = "/addFoodItems";
	
	public static final String UPDATE_RESTAURANT = "/updateRestaurant";
	
	public static final String DELETE_RESTAURANT = "/deleteRestaurant";
	
	public static final String GET_ALL_RESTAURANT = "/getAllRestaurant";
	
	public static final String GET_MENU_FOR_RESTAURANT = "/getMenuForRestaurant/{restaurant_id}";
	
	public static final String COMMON_CONTROLLER = "/api/common";
	
	public static final String SEARCH = "/search";
	
	public static final String CATEGORY_CONTROLLER = "/api/category";
	
	public static final String NEW_CATEGORY = "/newCategory";
	
	public static final String UPDATE_CATEGORY = "/updateCategory";
	
	public static final String UPDATE_ACTIVE_NOW = "/update_active_now";
	
	public static final String UPDATE_FOOD_ITEMS = "/update_food_items";
	
	public static final String SUBMIT_FOOD_RATING = "/submit_rating";
	
	public static final String RESTAURANT_REVIEWS_NAME = "restaurant_reviews";
	
	public static final String SUBMIT_RESTAURANT_RATING = "/submit_restaurant_review";
}
