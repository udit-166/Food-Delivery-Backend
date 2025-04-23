package com.order.track.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.order.track.adapter.model.PaymentDTO;
import com.order.track.core.entity.Payment;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PaymentMapper {
	
	PaymentDTO entityToDto(Payment payment);
}
