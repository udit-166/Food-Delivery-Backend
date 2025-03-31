package com.auth.user.adapter.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.user.adapter.mapper.UserMapper;
import com.auth.user.core.entity.Address;
import com.auth.user.core.entity.User;
import com.auth.user.core.model.UserDto;
import com.auth.user.core.repository.AddressRepositories;
import com.auth.user.core.repository.UserRepositories;

@Service
public class UserRepositoryImpl implements UserRepository{
	
	@Autowired
	private UserRepositories userRepository;
	
	@Autowired
	private AddressRepositories addressRepository;
	
	private UserMapper userMapper;

	@Override
	public User save(User userData) {
		return userRepository.save(userData);
	}

	@Override
	public Optional<User> findById(UUID id) {
		return userRepository.findById(id);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void deleteById(UUID id) {
			userRepository.deleteById(id);
	}

	@Override
	public List<Address> getAddressByUserId(UUID userId) {
		return addressRepository.findByUserId(userId);
	}

	@Override
	public User findUserByPhoneNumber(String phoneNumber) {
		User user = userRepository.findByPhone(phoneNumber);
		return user;
	}

	@Override
	public User findByGoogleId(String googleId) {
		User user = userRepository.findByGoogleId(googleId);
		return user;
	}

}
