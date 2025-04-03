package com.auth.user.adapter.repository;

import java.util.UUID;

import com.auth.user.core.entity.Address;

public interface AddressRepository {
	
	Address save(Address address);
	Address findById(UUID addressId);
}
