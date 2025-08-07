package com.food.restaurant.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.food.restaurant.adapter.model.UpdateCategoryRequestDto;
import com.food.restaurant.core.entity.Categories;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CategoryMapper {

	UpdateCategoryRequestDto entityToDto(Categories category);
	
	
	Categories dtoToEntity(UpdateCategoryRequestDto updateCategory);
	
}
