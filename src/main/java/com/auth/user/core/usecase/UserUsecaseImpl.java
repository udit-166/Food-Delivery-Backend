package com.auth.user.core.usecase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.user.adapter.mapper.UserMapper;
import com.auth.user.adapter.repository.AddressRepository;
import com.auth.user.adapter.repository.UserRepository;
import com.auth.user.core.entity.Address;
import com.auth.user.core.entity.User;
import com.auth.user.core.model.Location;
import com.auth.user.core.model.Role;
import com.auth.user.core.model.UpdateMetaDataRequest;
import com.auth.user.core.model.UserDto;
import com.auth.user.core.utils.Haversine;

@Service
public class UserUsecaseImpl implements UserUsecase{
	
	private UserRepository userRepository;
	
	private AddressRepository addressRepository;
	
	private UserMapper userMapper;
	private Haversine haversine;
	
	private UserUsecaseImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
		
	}

	@Override
	public UserDto findUserByPhoneNumberOrEmail(String phone_number, String email) {
		User user = new User();
		if(phone_number  != null) {
			user = userRepository.findUserByPhoneNumber(phone_number);
		}
		else {
			user = userRepository.findUserByEmail(email);
		}
		UserDto resultantUser = userMapper.entityToDto(user);
		return resultantUser;
	}

	@Override
	public UserDto updateuserProfile(UserDto userForEdit) {
		 Optional<User> user = userRepository.findById(userForEdit.getId());
		 
		 if(user.isPresent()) {
			 User userEdit = new User();
			 userEdit = user.get();
			 userEdit.setUsername(userForEdit.getUsername());
			 userEdit.setEmail(userForEdit.getEmail());
			 userEdit.setPhone(userForEdit.getPhone());
			 userEdit.setProfileImageUrl(userForEdit.getProfileImageUrl());
			 userEdit.setRole(userForEdit.getRole());
			 userEdit.setVerified(true);
			 
			 User result = userRepository.save(userEdit);
			 
			 UserDto updatedUser = userMapper.entityToDto(result);
			 
			 return updatedUser;
			 
		 }
		 return null;
	}

	@Override
	public Role getUserRole(UUID userId) {
		Optional<User> user = userRepository.findById(userId);
		 
		 if(user.isPresent()) {
			 User existUser = user.get();
			 return existUser.getRole();
		 }
		return null;
	}

	@Override
	public Address getCurrentaddress(Location location, UUID userId) {
		
		List<Address> addresses = userRepository.getAddressByUserId(userId);
		
		if(addresses.isEmpty()) {
			return null;
		}
		
		Address closestAddress = null;
		double minDistance = Double.MAX_VALUE;
		
		for(Address address : addresses) {
			double distance = haversine.haversine(location.getLatitude(), location.getLongitude(), address.getLatitude(), address.getLongitude());
			
			if(distance < minDistance) {
				minDistance = distance;
				closestAddress = address;
			}
		}
		return closestAddress;
	}

	@Override
	public List<Address> getAllAddressOfUser(UUID userId) {
		
		List<Address> address = userRepository.getAddressByUserId(userId);
		
		if(address.isEmpty()) {
			return null;
		}
		return address;
	}

	@Override
	public List<Address> saveAddress(UUID userId, Address address) {
		
		Optional<User> user = userRepository.findById(userId);
		
		if(user.isPresent()) {
			User exisingUser = user.get();
			 address.setUser(exisingUser);
			 
			 addressRepository.save(address);
			 
			 List<Address> result = userRepository.getAddressByUserId(userId);
			 if(result.isEmpty()) {
				 return null;
			 }
			 return result;
		}
		return null;
		
	}

	@Override
	public List<Address> updatedAddress(Address address) {
		Address result = addressRepository.findById(address.getId());
		if(result==null) {
			return null;
		}
		result.setAddress(address.getAddress());
		result.setLatitude(address.getLatitude());
		result.setLongitude(address.getLongitude());
		
		addressRepository.save(result);
		
		UUID userId = result.getUser().getId();
		List<Address> addresses = userRepository.getAddressByUserId(userId);
		return addresses;
	}

	@Override
	public UserDto updateUserMetaDataInfo(UpdateMetaDataRequest metaData) {
		 Optional<User> user = userRepository.findById(metaData.getId());
		 
		 if(user.isPresent()) {
			 User existingUser = user.get();
			 existingUser.setApp_version(metaData.getApp_version());
			 existingUser.setDevice_id(metaData.getDevice_id());
			 existingUser.setFcmToken(metaData.getFcmToken());
			 
			 userRepository.save(existingUser);
			 
			 UserDto result = userMapper.entityToDto(existingUser);
			 return result;
		 }
		return null;
	}

	@Override
	public UserDto deActivateUser(UUID userId) {
		 Optional<User> user = userRepository.findById(userId);
		 
		 if(user.isPresent()) {
			 User existingUser = user.get();
			 existingUser.setActive(false);
			 
			User temp =  userRepository.save(existingUser);
			UserDto result = userMapper.entityToDto(temp);
			return result;
		 }
		 return null;
	}

	
}
