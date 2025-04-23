package com.order.track.adapter.model;

import java.util.UUID;

import lombok.Data;

@Data
public class VerifyPaymentRequest {

	private String razorpayOrderId;
	
	private String razorpayPaymentId;
	
	private String razorpaySignature;
	
	private UUID orderId;
	
	private UUID paymentId;
	
}
