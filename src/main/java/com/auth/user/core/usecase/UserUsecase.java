package com.auth.user.core.usecase;

import java.util.List;
import java.util.UUID;

import com.auth.user.core.entity.Address;
import com.auth.user.core.entity.User;
import com.auth.user.core.model.Location;
import com.auth.user.core.model.Role;
import com.auth.user.core.model.UpdateMetaDataRequest;
import com.auth.user.core.model.UserDto;

public interface UserUsecase {

	public User findUserByPhoneNumberOrEmail(String phone_number, String email);
	public User updateuserProfile(UserDto userForEdit);
	public Role getUserRole(UUID userId);
	public Address getCurrentaddress(Location location, UUID userId);
	public List<Address> getAllAddressOfUser(UUID userId);
	public List<Address> saveAddress(UUID userId, Address address);
	public List<Address> updatedAddress(Address address);
	public User updateUserMetaDataInfo(UpdateMetaDataRequest metaData);
	public User deActivateUser(UUID userId);
}
