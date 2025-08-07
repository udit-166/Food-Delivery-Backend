package com.auth.user.core.model;

import java.util.UUID;

import lombok.Data;

@Data
public class GetCurrentAddressRequest {

	private Location location;
	
	private UUID userId;
}
