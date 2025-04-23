package com.order.track.adapter.model;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class PaymentDTO {

	 private UUID id;
		
	 private UUID orderId;
	 
	 private String razorpayOrderId;
	 
	 private String key;
	 
	 private BigDecimal amount;
	 
	 private String currency;
}
