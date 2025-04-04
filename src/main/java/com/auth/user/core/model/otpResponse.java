package com.auth.user.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class otpResponse {

	private String otp;
	
	private String message;
}
