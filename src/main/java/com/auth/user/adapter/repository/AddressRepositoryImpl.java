package com.auth.user.adapter.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.auth.user.core.entity.Address;
import com.auth.user.core.repository.AddressRepositories;

@Repository
public class AddressRepositoryImpl implements AddressRepository{
	
	@Autowired
	private AddressRepositories addressRepositories;

	@Override
	public Address save(Address address) {
		
		Address result = addressRepositories.save(address);
		
		return result;
	}

	@Override
	public Address findById(UUID addressId) {
		
		Optional<Address> result = addressRepositories.findById(addressId);
		if(result.isPresent()) {
			return result.get();
		}
		return null;
	}

}
