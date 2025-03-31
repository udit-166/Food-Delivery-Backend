package com.auth.user.core.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.user.adapter.mapper.UserMapper;
import com.auth.user.adapter.repository.UserRepository;
import com.auth.user.core.entity.User;
import com.auth.user.core.model.UserDto;
import com.auth.user.core.utils.JwtAuthentication;


@Service
public class AuthUsecaseImpl implements AuthUsecase{
	
	private UserRepository userRepository;
	
	private UserMapper userMapper;
	
	private JwtAuthentication jwtAuthentication;
	
	private AuthUsecaseImpl(UserRepository userRepository, JwtAuthentication jwtAuthentication) {
		this.userRepository = userRepository;
		this.jwtAuthentication = jwtAuthentication;
	}

	@Override
	public UserDto login(String phoneNumber) {
		User user = userRepository.findUserByPhoneNumber(phoneNumber);
		
		UserDto loginResponse = userMapper.entityToDto(user);
		
		return loginResponse;
	}

	@Override
	public UserDto register(User user) {
		 
		User payload = new User();
		
		payload.setEmail(user.getEmail());
		payload.setPhone(user.getPhone());
		payload.setLocation(user.getLocation());
		payload.setDevice_id(user.getDevice_id());
		payload.setProfileImageUrl(user.getProfileImageUrl());
		payload.setActive(true);
		payload.setVerified(true);
		
		userRepository.save(payload);
		
		UserDto registerUser = userMapper.entityToDto(payload);
		
		return registerUser;
	}

	@Override
	public UserDto findByGoogleId(String googleId) {
		User userByGoogleId = userRepository.findByGoogleId(googleId);
		
		UserDto userByGoogleRegistration = userMapper.entityToDto(userByGoogleId);
		
		return userByGoogleRegistration;
	}

	@Override
	public String refreshToken(String phone_number) {
		String token = jwtAuthentication.generateToken(phone_number);
		return token;
	}

	
}
