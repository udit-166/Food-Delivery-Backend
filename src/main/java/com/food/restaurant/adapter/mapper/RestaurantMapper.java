package com.food.restaurant.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.food.restaurant.adapter.model.RestaurantDto;
import com.food.restaurant.core.entity.Restaurant;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RestaurantMapper {
	RestaurantDto entityToDto(Restaurant restaurant);
	Restaurant dtoToEntity(RestaurantDto restaurantDto);
}
