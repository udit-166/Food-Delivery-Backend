package com.auth.user.core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.auth.user.adapter.service.UserService;
import com.auth.user.common.constant.AppConstants;
import com.auth.user.core.entity.Address;
import com.auth.user.core.model.AddressResponse;
import com.auth.user.core.model.DeactivateUserResponse;
import com.auth.user.core.model.Location;
import com.auth.user.core.model.Role;
import com.auth.user.core.model.RoleResponse;
import com.auth.user.core.model.UpdateMetaDataRequest;
import com.auth.user.core.model.UpdatedMetaDataResponse;
import com.auth.user.core.model.UserDetailsResponse;
import com.auth.user.core.model.UserDto;

@RestController
@RequestMapping(AppConstants.USER_CONTROLLER)
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(AppConstants.GET_PROFILE_BY_PHONE_NUMBER)
	public ResponseEntity<UserDetailsResponse> getProfileByPhoneNumber(@PathVariable String phone_number){
		try {
			UserDetailsResponse response  = new UserDetailsResponse();
			UserDto user = userService.findUserByPhoneNumberOrEmail(phone_number, null);
			if(user == null) {
				response.setMessage("User didn't found!");
				return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
			}
			response.setUser(user);
			response.setMessage("User details fetched successfully!");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			UserDetailsResponse response = new UserDetailsResponse();
			e.printStackTrace();
			response.setUser(null);
			response.setMessage("Something went wrong!!");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(AppConstants.GET_PROFILE_BY_EMAIL)
	public ResponseEntity<UserDetailsResponse> getProfileByEmail(@PathVariable String email){
		try {
			UserDetailsResponse response  = new UserDetailsResponse();
			UserDto user = userService.findUserByPhoneNumberOrEmail(null, email);
			if(user == null) {
				response.setMessage("User didn't found!");
				return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
			}
			response.setUser(user);
			response.setMessage("User details fetched successfully!");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			UserDetailsResponse response = new UserDetailsResponse();
			e.printStackTrace();
			response.setUser(null);
			response.setMessage("Something went wrong!!");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(AppConstants.UPDATE_PROFILE)
	public ResponseEntity<UserDetailsResponse> updateProfile(@RequestParam UserDto user){
		try {
		UserDto updatedUser = userService.updateUserProfile(user);
		UserDetailsResponse res = new UserDetailsResponse();
		if(updatedUser == null) {
			res.setUser(null);
			res.setMessage("User not found!!");
			return new ResponseEntity<UserDetailsResponse>(res, HttpStatus.BAD_REQUEST);
		}
		res.setUser(updatedUser);
		res.setMessage("The user has been updated successfully!!");
		return new ResponseEntity<>(res, HttpStatus.OK);
		}
		catch (Exception e) {
			UserDetailsResponse  res = new UserDetailsResponse();
			e.printStackTrace();
			res.setUser(null);
			res.setMessage("The error occur while executing the service!!");
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(AppConstants.GET_ROLE)
	public ResponseEntity<RoleResponse> getRole (@RequestParam UUID userId){
		try {
			RoleResponse response = new RoleResponse();
			Role role = userService.getUserRole(userId);
			
			if(role==null) {
				response.setRole(null);
				response.setMessage("The Role didn't found!!");
				return new ResponseEntity<RoleResponse>(response, HttpStatus.BAD_REQUEST);
			}
			response.setRole(role);
			response.setMessage("Role details fetched successfully!!");
			return new ResponseEntity<RoleResponse>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			RoleResponse response = new RoleResponse();
			e.printStackTrace();
			response.setMessage("Error while fetching the role service.");
			response.setRole(null);
			return new ResponseEntity<RoleResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(AppConstants.GET_CURRENT_ADDRESS)
	public ResponseEntity<AddressResponse> getCurrentAddress(@RequestParam Location location, @PathVariable UUID userId){
		try {
			AddressResponse res = new AddressResponse();
			ArrayList<Address> currentAddress= new ArrayList<>();
			
			Address address = userService.getCurrentAddress(location, userId);
			
			if(address == null) {
				res.setAddresses(null);
				res.setMessage("The current address didn't found!!");
				return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
			}
			
			currentAddress.add(address);
			res.setAddresses(currentAddress);
			res.setMessage("Cuurent address fetched successfully!!");
			
			return new ResponseEntity<>(res, HttpStatus.OK);
			
		} catch (Exception e) {
			AddressResponse res = new AddressResponse();
			res.setAddresses(null);
			res.setMessage("Internal server error!!");
			
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(AppConstants.GET_ALL_ADDRESS_OF_USER)
	public ResponseEntity<AddressResponse> getAllAddressOfUser(@RequestParam UUID userID){
		try {
			AddressResponse response = new AddressResponse();
			List<Address> address= userService.getAllAddressOfUser(userID);
			if(address.isEmpty()) {
				response.setAddresses(null);
				response.setMessage("The address are empty!");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
			ArrayList<Address> result = new ArrayList<>(address);
			
			response.setAddresses(result);
			response.setMessage("The address list fetched successfully!!");
			
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			AddressResponse response = new AddressResponse();
			response.setAddresses(null);
			response.setMessage("The error occur in code!!");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(AppConstants.SAVE_ADDRESS)
	public ResponseEntity<AddressResponse> saveAddress(@RequestParam UUID userId , @RequestParam Address address){
		try {
			AddressResponse response = new AddressResponse();
			List<Address> address1= userService.saveAddress(userId, address);
			if(address1.isEmpty()) {
				response.setAddresses(null);
				response.setMessage("The address are empty!");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
			ArrayList<Address> result = new ArrayList<>(address1);
			
			response.setAddresses(result);
			response.setMessage("The address saved successfully!!");
			
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			AddressResponse response = new AddressResponse();
			response.setAddresses(null);
			response.setMessage("The error occur in code!!");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(AppConstants.UPDATE_ADDRESS)
	public ResponseEntity<AddressResponse> updateAddress(@RequestParam Address address){
		try {
			AddressResponse response = new AddressResponse();
			List<Address> address1= userService.updatedAddress(address);
			if(address1.isEmpty()) {
				response.setAddresses(null);
				response.setMessage("The address are empty!");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
			ArrayList<Address> result = new ArrayList<>(address1);
			
			response.setAddresses(result);
			response.setMessage("The address updated successfully!!");
			
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			AddressResponse response = new AddressResponse();
			response.setAddresses(null);
			response.setMessage("The error occur in code!!");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(AppConstants.UPDATE_USER_META_DATA)
	public ResponseEntity<UpdatedMetaDataResponse> updateUserMetaData(@RequestParam UpdateMetaDataRequest dataRequest){
		try {
			UserDto user = userService.updateUserMetaDataInfo(dataRequest);
			UpdatedMetaDataResponse response = new UpdatedMetaDataResponse();
			if(user==null) {
				response.setMessage("The user didn't found!");
				response.setApp_version(dataRequest.getApp_version());
				response.setDevice_id(dataRequest.getDevice_id());
				response.setFcmToken(dataRequest.getFcmToken());
				response.setId(dataRequest.getId());
				return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
			}
			response.setMessage("The user meta data has been updated successfully!");
			response.setApp_version(user.getApp_version());
			response.setDevice_id(user.getDevice_id());
			response.setFcmToken(user.getFcmToken());
			response.setId(user.getId());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			UpdatedMetaDataResponse response = new UpdatedMetaDataResponse();
			response.setMessage("The service didn't run due to error!");
			response.setApp_version(dataRequest.getApp_version());
			response.setDevice_id(dataRequest.getDevice_id());
			response.setFcmToken(dataRequest.getFcmToken());
			response.setId(dataRequest.getId());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(AppConstants.DELETE_ACCOUNT)
	public ResponseEntity<DeactivateUserResponse> deActivateUser(@RequestParam UUID userId){
		try {
			DeactivateUserResponse response = new DeactivateUserResponse();
			UserDto user = userService.deActivateUser(userId);
			if(user == null) {
				response.setId(userId);
				response.setActive(true);
				response.setMessage("the user not found!!");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
			
			response.setId(userId);
			response.setActive(false);
			response.setMessage("The user deleted successfully!");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			DeactivateUserResponse response = new DeactivateUserResponse();
			response.setId(userId);
			response.setActive(true);
			response.setMessage("Error occur in this service");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
