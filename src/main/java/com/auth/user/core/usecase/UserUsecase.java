package com.auth.user.core.usecase;

import java.util.List;
import java.util.UUID;

import com.auth.user.core.entity.Address;
import com.auth.user.core.model.Location;
import com.auth.user.core.model.Role;
import com.auth.user.core.model.UserDto;

public interface UserUsecase {

	public UserDto findUserByPhoneNumberOrEmail(String phone_number, String email);
	public UserDto updateuserProfile(UserDto userForEdit);
	public Role getUserRole(UUID userId);
	public Address getCurrentaddress(Location location, UUID userId);
	public List<Address> getAllAddressOfUser(UUID userId);
	public List<Address> saveAddress(UUID userId, Address address);
	public List<Address> updatedAddress(Address address);
}
