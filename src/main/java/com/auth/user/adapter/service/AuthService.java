package com.auth.user.adapter.service;

import java.util.UUID;

import com.auth.user.core.entity.User;
import com.auth.user.core.model.UserDto;

public interface AuthService {

	public UserDto login(String phoneNumber);
	
	public UserDto register(User user); 
	
	public UserDto findByGoogleId(String googleId);
	
	public String refreshJwtToken(String phone);
}
