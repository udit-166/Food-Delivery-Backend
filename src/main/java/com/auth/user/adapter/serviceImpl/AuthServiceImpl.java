package com.auth.user.adapter.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.auth.user.adapter.service.AuthService;
import com.auth.user.core.entity.User;
import com.auth.user.core.model.UserDto;
import com.auth.user.core.usecase.AuthUsecase;

@Service
public class AuthServiceImpl implements AuthService{

	@Autowired
	private AuthUsecase authUsecase;
	
	@Override
	public User login(String phoneNumber) {
		return authUsecase.login(phoneNumber);
	}

	@Override
	public User register(User user) {
		return authUsecase.register(user);
	}

	@Override
	public User findByGoogleId(String googleId) {
		return authUsecase.findByGoogleId(googleId);
	}

	@Override
	public String refreshJwtToken(String phone) {
		return authUsecase.refreshToken(phone);
	}

	@Override
	public String generateOtp(String phone_number) {
		return authUsecase.generateOtp(phone_number);
	}

	@Override
	public void sendOtp(String phone_number) {
		authUsecase.sendOtp(phone_number);
		
	}

	@Override
	public String getOtp(String phoneNumber) {
		return authUsecase.getOtp(phoneNumber);
	}

	@Override
	public boolean validateOtp(String phoneNumber, String otp) {
		return authUsecase.validateOtp(phoneNumber, otp);
	}

	@Override
	public void deleteOtp(String phoneNumber) {
		authUsecase.deleteOtp(phoneNumber);
		
	}

	
}
