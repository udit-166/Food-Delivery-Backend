package com.auth.user.adapter.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.auth.user.core.entity.Address;
import com.auth.user.core.entity.User;


public interface UserRepository {
	
	User save(User userData);
	
	Optional<User> findById(UUID id);
	
	List<User> findAll();
	
	void deleteById(UUID id);
	
	List<Address> getAddressByUserId(UUID userId);
	
	User findUserByPhoneNumber(String phoneNumber);
	
	User findByGoogleId(String googleId);
	
}
