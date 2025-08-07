package com.auth.user.core.model;

import java.util.UUID;

import com.auth.user.core.entity.Address;

import lombok.Data;

@Data
public class AddAddressRequest {

	private Address address;
	
	private UUID userId;
}
