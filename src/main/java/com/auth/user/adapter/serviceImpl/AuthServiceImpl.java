package com.auth.user.adapter.serviceImpl;

import org.springframework.stereotype.Service;

import com.auth.user.adapter.mapper.UserMapper;
import com.auth.user.adapter.service.AuthService;
import com.auth.user.core.entity.User;
import com.auth.user.core.model.RefreshTokenResponse;
import com.auth.user.core.model.UserDto;
import com.auth.user.core.usecase.AuthUsecase;


@Service
public class AuthServiceImpl implements AuthService{

	private final AuthUsecase authUsecase;
	private final UserMapper userMapper;
	
	private AuthServiceImpl(AuthUsecase authUsecase, UserMapper userMapper) {
		this.authUsecase = authUsecase;
		this.userMapper = userMapper;
	}
	
	@Override
	public UserDto login(String phoneNumber) {
		User user =  authUsecase.login(phoneNumber);
		return userMapper.entityToDto(user);
	}

	@Override
	public UserDto register(User user) {
		User user1 =  authUsecase.register(user);
		
		return userMapper.entityToDto(user1);
	}

	@Override
	public UserDto findByGoogleId(String googleId) {
	
		User user =  authUsecase.findByGoogleId(googleId);
		
		return userMapper.entityToDto(user);
	}

	@Override
	public RefreshTokenResponse refreshJwtToken(String phone) {
		String token =  authUsecase.refreshToken(phone);
		RefreshTokenResponse res = new RefreshTokenResponse();
		res.setToken(token);
		res.setMessgae("Refresh token generated successfully!!");
		return res;
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
