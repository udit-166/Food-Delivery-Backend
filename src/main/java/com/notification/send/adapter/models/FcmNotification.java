package com.notification.send.adapter.models;

import lombok.Data;

@Data
public class FcmNotification {
	
	private String to;
	
	private String title;
	
	private String body;

}
