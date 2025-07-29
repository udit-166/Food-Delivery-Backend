package com.auth.user.core.model;

import com.auth.user.core.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {
	
	private UserDto user;
	
	private String message;
	
	private String jwt;
}
