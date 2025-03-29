package com.auth.user.core.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.user.adapter.mapper.UserMapper;
import com.auth.user.adapter.repository.UserRepository;
import com.auth.user.core.entity.User;
import com.auth.user.core.model.UserDto;


@Service
public class AuthUsecaseImpl implements AuthUsecase{
	
	private UserRepository userRepository;
	
	private UserMapper userMapper;
	
	private AuthUsecaseImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDto login(String phoneNumber) {
		User user = userRepository.findUserByPhoneNumber(phoneNumber);
		
		UserDto loginResponse = userMapper.entityToDto(user);
		
		return loginResponse;
	}

	
}
