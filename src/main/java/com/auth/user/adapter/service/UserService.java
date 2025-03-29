package com.auth.user.adapter.service;

import com.auth.user.core.model.UserDto;

public interface UserService {

	public UserDto login(String phoneNumber);
}
