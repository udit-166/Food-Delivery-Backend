package com.food.restaurant.adapter.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.food.restaurant.adapter.model.FoodItemDto;
import com.food.restaurant.core.entity.FoodItem;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface FoodItemMapper {
	FoodItemDto entityToDto(List<FoodItem> foodItems);
	FoodItem dtoToEntity(FoodItemDto foodItemDto);
}