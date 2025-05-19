package com.auth.user.core.model;

import lombok.Data;

@Data
public class EmailRequest {

	private String message;
	
	private String to;
	
	private String subject;
}
