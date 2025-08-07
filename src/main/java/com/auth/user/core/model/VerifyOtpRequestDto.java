package com.auth.user.core.model;

import lombok.Data;

@Data
public class VerifyOtpRequestDto {

	private String phone_number;
	
	private String otp;
}
