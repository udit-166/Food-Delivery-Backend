package com.auth.user.core.model;

import lombok.Data;

@Data
public class RefreshTokenResponse {

	private String token;
	
	private String messgae;
}
