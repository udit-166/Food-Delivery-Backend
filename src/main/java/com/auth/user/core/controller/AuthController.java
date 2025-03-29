package com.auth.user.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth.user.adapter.service.UserService;
import com.auth.user.core.entity.User;
import com.auth.user.core.model.LoginResponse;
import com.auth.user.core.model.UserDto;
import com.auth.user.core.utils.JwtAuthentication;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final JwtAuthentication jwtAuthentication ;
	
	@Autowired
	private UserService userService;
	
	public AuthController(JwtAuthentication jwtAuthentication) {
		this.jwtAuthentication = jwtAuthentication;
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestParam String phoneNumber){
		try {
		LoginResponse response = new LoginResponse();
		if(phoneNumber != null) {
			UserDto user = userService.login(phoneNumber);
			
			if(user!=null) {
				String token = jwtAuthentication.generateToken(phoneNumber);
				response.setUser(user);
				response.setMessage("You have login successfully!");
				response.setJwt(token);
				
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			else {
				response.setMessage("User didn't find with this phone number");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
		}
		else {
			response.setMessage("The phone number is empty!!");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		}
		catch (Exception e) {
			e.printStackTrace(); // For debugging, log the stack trace (use a logger in production)
		    LoginResponse response = new LoginResponse();
		    response.setMessage("An error occurred during login: " + e.getMessage());
		    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
