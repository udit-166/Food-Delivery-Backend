package com.auth.user.core.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth.user.core.entity.Address;
import com.auth.user.core.model.Location;
import com.auth.user.core.model.UserDto;

@RestController
@RequestMapping("/user")
public class UserController {

	@GetMapping("/getProfileDetails")
	public ResponseEntity<?> getProfile(@RequestParam UUID userId){
		return null;
	}
	
	@PutMapping("/updateProfile")
	public ResponseEntity<?> updateProfile(@RequestParam UserDto user){
		return null;
	}
	
	@GetMapping("/getRoleOfUser")
	public ResponseEntity<?> getRole (@RequestParam UUID userId){
		return null;
	}
	
	@GetMapping("/getCurrentAddress")
	public ResponseEntity<Address> getCurrentAddress(@RequestParam Location location){
		return null;
	}
	
	@GetMapping("/getListOfAddress")
	public ResponseEntity<?> getAllAddressOfUser(@RequestParam UUID userID){
		return null;
	}
	
	@PostMapping("/saveAddress")
	public ResponseEntity<?> saveAddress(@RequestParam UUID userId , @RequestParam Address address){
		return null;
	}
	
	@PutMapping("/updateAddress/{addressId}")
	public ResponseEntity<?> updateAddress(@PathVariable UUID addressId, @RequestParam Address address){
		return null;
	}
	
}
