package com.auth.user.core.usecase;

import com.auth.user.core.entity.User;
import com.auth.user.core.model.UserDto;

public interface AuthUsecase {

	public UserDto login(String phoneNumber);
	
	public UserDto register (User user);

	public UserDto findByGoogleId(String googleId);
	
	public String refreshToken(String phone_number);
}
