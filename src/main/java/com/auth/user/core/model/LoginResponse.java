package com.auth.user.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {
	
	private UserDto user;
	
	private String message;
	
	private String jwt;
}
