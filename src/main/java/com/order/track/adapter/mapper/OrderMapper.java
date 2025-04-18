package com.order.track.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.order.track.adapter.model.OrderDTO;
import com.order.track.core.entity.Order;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface OrderMapper {
	
	OrderDTO entityToDto(Order order);
	
	Order dtoToEntity(OrderDTO orderDTO);
}
