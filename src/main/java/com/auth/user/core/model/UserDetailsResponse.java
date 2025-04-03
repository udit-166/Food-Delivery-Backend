package com.auth.user.core.model;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetailsResponse {

	private UserDto user;
	private String message;
}
