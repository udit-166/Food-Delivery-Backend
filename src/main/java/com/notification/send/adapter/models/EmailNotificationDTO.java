package com.notification.send.adapter.models;

import lombok.Data;

@Data
public class EmailNotificationDTO {

	private String message;
	
	private String to;
	
	private String subject;
	
}

