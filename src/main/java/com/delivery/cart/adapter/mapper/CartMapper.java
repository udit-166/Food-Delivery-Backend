package com.delivery.cart.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.delivery.cart.adapter.models.CartDto;
import com.delivery.cart.core.models.Cart;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CartMapper {

	CartDto entityToDto(Cart cart);
	Cart dtoToEntity(CartDto cartDto);
}
