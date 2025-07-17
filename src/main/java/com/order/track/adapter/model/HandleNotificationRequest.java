package com.order.track.adapter.model;

import lombok.Data;

@Data
public class HandleNotificationRequest {

	private String orderId;
	
	private String email;
	
	private String fcmToken;
	
	private String phone;
}
