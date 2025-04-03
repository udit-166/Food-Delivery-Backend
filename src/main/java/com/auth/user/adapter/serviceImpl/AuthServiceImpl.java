package com.auth.user.adapter.serviceImpl;

import java.util.UUID;

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
	public UserDto login(String phoneNumber) {
		return authUsecase.login(phoneNumber);
	}

	@Override
	public UserDto register(User user) {
		return authUsecase.register(user);
	}

	@Override
	public UserDto findByGoogleId(String googleId) {
		return authUsecase.findByGoogleId(googleId);
	}

	@Override
	public String refreshJwtToken(String phone) {
		return authUsecase.refreshToken(phone);
	}

	
}
