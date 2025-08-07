package com.auth.user.adapter.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.user.adapter.mapper.UserMapper;
import com.auth.user.adapter.service.UserService;
import com.auth.user.core.entity.Address;
import com.auth.user.core.model.Location;
import com.auth.user.core.model.Role;
import com.auth.user.core.model.UpdateMetaDataRequest;
import com.auth.user.core.model.UserDto;
import com.auth.user.core.usecase.UserUsecase;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserUsecase userUsecase;
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDto findUserByPhoneNumberOrEmail(String phone_number, String email) {
		UserDto user = userMapper.entityToDto(userUsecase.findUserByPhoneNumberOrEmail(phone_number, email));
		
		return user;
	}

	@Override
	public UserDto updateUserProfile(UserDto userForEdit) {
		
		return userMapper.entityToDto(userUsecase.updateuserProfile(userForEdit)) ;
	}

	@Override
	public Role getUserRole(UUID userId) {
		return userUsecase.getUserRole(userId);
	}

	@Override
	public Address getCurrentAddress(Location location, UUID userId) {
		
		return userUsecase.getCurrentaddress(location, userId);
	}

	@Override
	public List<Address> getAllAddressOfUser(UUID userId) {
		return userUsecase.getAllAddressOfUser(userId);
	}

	@Override
	public List<Address> saveAddress(UUID userId, Address address) {
		return userUsecase.saveAddress(userId, address);
	}

	@Override
	public List<Address> updatedAddress(Address address) {
		
		return userUsecase.updatedAddress(address);
	}

	@Override
	public UserDto updateUserMetaDataInfo(UpdateMetaDataRequest metaData) {
		return userMapper.entityToDto(userUsecase.updateUserMetaDataInfo(metaData));
	}

	@Override
	public UserDto deActivateUser(UUID userId) {
		return userMapper.entityToDto(userUsecase.deActivateUser(userId));
	}
	

}
