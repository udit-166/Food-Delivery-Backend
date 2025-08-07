package com.order.track.adapter.model;

import java.util.UUID;

import lombok.Data;

@Data
public class InitiatePaymentRequestDto {
	
	private UUID order_id;

}
