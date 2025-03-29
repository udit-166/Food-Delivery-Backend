package com.auth.user.adapter.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.user.adapter.service.UserService;
import com.auth.user.core.model.UserDto;
import com.auth.user.core.usecase.AuthUsecase;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private AuthUsecase authUsecase;
	
	@Override
	public UserDto login(String phoneNumber) {
		return authUsecase.login(phoneNumber);
	}

}
