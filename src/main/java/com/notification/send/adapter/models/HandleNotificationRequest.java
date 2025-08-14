package com.notification.send.adapter.models;

import lombok.Data;

@Data
public class HandleNotificationRequest {

	private String orderId;
	
	private String email;
	
	private String fcmToken;
	
	private String phone;
	
}
