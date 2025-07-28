package com.auth.user.adapter.service;

import java.util.UUID;

import com.auth.user.core.entity.User;
import com.auth.user.core.model.UserDto;

public interface AuthService {

	public User login(String phoneNumber);
	
	public User register(User user); 
	
	public User findByGoogleId(String googleId);
	
	public String refreshJwtToken(String phone);
	
	public String generateOtp(String phone_number);
	
	public void sendOtp(String phone_number);
	
	public String getOtp(String phoneNumber);
	
	public boolean validateOtp(String phoneNumber, String otp);
	
	public void deleteOtp(String phoneNumber);
}
