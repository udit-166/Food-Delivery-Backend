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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.auth.user.adapter.service.UserService;
import com.auth.user.common.constant.AppConstants;
import com.auth.user.core.entity.Address;
import com.auth.user.core.model.AddAddressRequest;
import com.auth.user.core.model.AddressResponse;
import com.auth.user.core.model.DeactivateUserResponse;
import com.auth.user.core.model.GenericResponse;
import com.auth.user.core.model.GetCurrentAddressRequest;
import com.auth.user.core.model.Location;
import com.auth.user.core.model.Role;
import com.auth.user.core.model.RoleResponse;
import com.auth.user.core.model.StatusCode;
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
	public GenericResponse<UserDetailsResponse> getProfileByPhoneNumber(@PathVariable String phone_number){
		try {
			UserDetailsResponse response  = new UserDetailsResponse();
			UserDto user = userService.findUserByPhoneNumberOrEmail(phone_number, null);
			if(user == null) {
				return new GenericResponse<>("User didn't found!!",StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			response.setUser(user);
			return new GenericResponse<>("User details fetched successfully!",StatusCode.of(HttpStatus.OK),response);
		} catch (Exception e) {
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@GetMapping(AppConstants.GET_PROFILE_BY_EMAIL)
	public GenericResponse<UserDetailsResponse> getProfileByEmail(@PathVariable String email){
		try {
			UserDetailsResponse response  = new UserDetailsResponse();
			UserDto user = userService.findUserByPhoneNumberOrEmail(null, email);
			if(user == null) {
				return new GenericResponse<>("User didn't found!", StatusCode.of(HttpStatus.BAD_REQUEST),null);
			}
			response.setUser(user);
			return new GenericResponse<>("User details fetched successfully!", StatusCode.of(HttpStatus.OK), response);
		} catch (Exception e) {
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PutMapping(AppConstants.UPDATE_PROFILE)
	public GenericResponse<UserDetailsResponse> updateProfile(@RequestBody UserDto user){
		try {
		UserDto updatedUser = userService.updateUserProfile(user);
		UserDetailsResponse res = new UserDetailsResponse();
		if(updatedUser == null) {
			res.setUser(null);
			return new GenericResponse<>("User not found for update!!", StatusCode.of(HttpStatus.BAD_REQUEST), null);
		}
		res.setUser(updatedUser);
		return new GenericResponse<>("The user has been updated successfully!!", StatusCode.of(HttpStatus.OK), res);
		}
		catch (Exception e) {
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
		
	}
	
	@GetMapping(AppConstants.GET_ROLE)
	public GenericResponse<RoleResponse> getRole (@RequestParam UUID userId){
		try {
			RoleResponse response = new RoleResponse();
			Role role = userService.getUserRole(userId);
			
			if(role==null) {
				return new GenericResponse<>("The Role didn't found!!", StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			response.setRole(role);
			return new GenericResponse<>("Role details fetched successfully!!", StatusCode.of(HttpStatus.OK), response);
			
		} catch (Exception e) {
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PostMapping(AppConstants.GET_CURRENT_ADDRESS)
	public GenericResponse<AddressResponse> getCurrentAddress(@RequestBody GetCurrentAddressRequest request){
		try {
			AddressResponse res = new AddressResponse();
			ArrayList<Address> currentAddress= new ArrayList<>();
			
			Address address = userService.getCurrentAddress(request.getLocation(), request.getUserId());
			
			if(address == null) {
				res.setAddresses(null);
				return new GenericResponse<>("The current address didn't found!!", StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			
			currentAddress.add(address);
			res.setAddresses(currentAddress);
			
			return new GenericResponse<>("Cuurent address fetched successfully!!", StatusCode.of(HttpStatus.OK), res);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@GetMapping(AppConstants.GET_ALL_ADDRESS_OF_USER)
	public GenericResponse<AddressResponse> getAllAddressOfUser(@RequestParam UUID userId){
		try {
			AddressResponse response = new AddressResponse();
			List<Address> address= userService.getAllAddressOfUser(userId);
			if(address.isEmpty()) {
				return new GenericResponse<>("The address are empty!",StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			ArrayList<Address> result = new ArrayList<>(address);
			
			response.setAddresses(result);
			
			return new GenericResponse<>("The address list fetched successfully!!", StatusCode.of(HttpStatus.OK), response);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PostMapping(AppConstants.SAVE_ADDRESS)
	public GenericResponse<AddressResponse> saveAddress(@RequestBody AddAddressRequest request ){
		try {
			AddressResponse response = new AddressResponse();
			List<Address> address1= userService.saveAddress(request.getUserId(), request.getAddress());
			if(address1.isEmpty()) {
				return new GenericResponse<>("The address are empty!", StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			ArrayList<Address> result = new ArrayList<>(address1);
			
			response.setAddresses(result);
			
			return new GenericResponse<>("The address saved successfully!!", StatusCode.of(HttpStatus.OK), response);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PutMapping(AppConstants.UPDATE_ADDRESS)
	public GenericResponse<AddressResponse> updateAddress(@RequestBody Address address){
		try {
			AddressResponse response = new AddressResponse();
			List<Address> address1= userService.updatedAddress(address);
			if(address1.isEmpty()) {
				response.setAddresses(null);
				return new GenericResponse<>("The address are empty!", StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			ArrayList<Address> result = new ArrayList<>(address1);
			
			response.setAddresses(result);
			
			return new GenericResponse<>("The address updated successfully!!", StatusCode.of(HttpStatus.OK),response);
			
		} catch (Exception e) {
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PutMapping(AppConstants.UPDATE_USER_META_DATA)
	public GenericResponse<UpdatedMetaDataResponse> updateUserMetaData(@RequestBody UpdateMetaDataRequest dataRequest){
		try {
			UserDto user = userService.updateUserMetaDataInfo(dataRequest);
			UpdatedMetaDataResponse response = new UpdatedMetaDataResponse();
			if(user==null) {
				response.setApp_version(dataRequest.getApp_version());
				response.setDevice_id(dataRequest.getDevice_id());
				response.setFcmToken(dataRequest.getFcmToken());
				response.setId(dataRequest.getId());
				return new GenericResponse<>("The user didn't found!", StatusCode.of(HttpStatus.ACCEPTED), response);
			}
			response.setApp_version(user.getApp_version());
			response.setDevice_id(user.getDevice_id());
			response.setFcmToken(user.getFcmToken());
			response.setId(user.getId());
			return new GenericResponse<>("The user meta data has been updated successfully!",StatusCode.of(HttpStatus.OK), response);
		} catch (Exception e) {
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@DeleteMapping(AppConstants.DELETE_ACCOUNT)
	public GenericResponse<DeactivateUserResponse> deActivateUser(@RequestParam UUID userId){
		try {
			DeactivateUserResponse response = new DeactivateUserResponse();
			UserDto user = userService.deActivateUser(userId);
			if(user == null) {
				response.setId(userId);
				response.setActive(true);
				return new GenericResponse<>("The user not found!!",StatusCode.of(HttpStatus.BAD_REQUEST), null);
				
				
			}
			
			response.setId(userId);
			response.setActive(false);
			return new GenericResponse<>("The user deleted successfully!",StatusCode.of(HttpStatus.OK), response);
		} catch (Exception e) {
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
}
