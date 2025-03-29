package com.auth.user.core.model;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

	private UUID id;
	
	private String username;
	
	private String email;
	
	private String phone;
	
	private Role role;
	
	private String profileImageUrl;
	
	private boolean isVerified;
	
	private String fcmToken;
	
	private String device_id;
}
