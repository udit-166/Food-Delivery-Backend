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
import com.auth.user.core.model.Location;
import com.auth.user.core.model.LoginResponse;
import com.auth.user.core.model.UserDto;
import com.auth.user.core.utils.GoogleTokenValidator;
import com.auth.user.core.utils.JwtAuthentication;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final JwtAuthentication jwtAuthentication ;
	
	private final GoogleTokenValidator googleTokenValidator;
	
	@Autowired
	private UserService userService;
	
	public AuthController(JwtAuthentication jwtAuthentication, GoogleTokenValidator googleTokenValidator) {
		this.jwtAuthentication = jwtAuthentication;
		this.googleTokenValidator = googleTokenValidator;
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestParam(required = false) String phoneNumber, @RequestParam(required = false) String oauthToken, @RequestParam Location location){

		try {
			LoginResponse response = new LoginResponse();
			if(oauthToken != null) {
				//google sign-in login flow
				Payload payload = googleTokenValidator.verifyGoogleToken(oauthToken);
				if(payload == null ) {
					response.setMessage("Invalid google login");
					return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
				}
				String email = payload.getEmail();
				String googleId = payload.getSubject();
				
				UserDto user = userService.findByGoogleId(googleId);
				
				if(user == null) {
					User userToRegister = new User();
					userToRegister.setUsername(payload.get("name").toString());
					userToRegister.setActive(true);
					userToRegister.setEmail(email);
					userToRegister.setGoogleId(googleId);
					userToRegister.setLocation(location);
					userToRegister.setVerified(true);
					
					UserDto registerUser = userService.register(userToRegister);
					String jwtToken = jwtAuthentication.generateToken(registerUser.getPhone());
					
					response.setUser(registerUser);
					response.setMessage("Google Sign-in successfully done!");
					response.setJwt(jwtToken);
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
				
				// generating the jwt token for exisiting google user
				
				String jwtToken = jwtAuthentication.generateToken(user.getEmail());
				response.setUser(user);
				response.setMessage("Google Sign-in done successfully!");
				response.setJwt(jwtToken);
				
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		
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
			response.setMessage("Either phoneNumber or googleToken is required");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			e.printStackTrace();
		    LoginResponse response = new LoginResponse();
		    response.setMessage("An error occurred during login: " + e.getMessage());
		    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<LoginResponse> registerUser(@RequestParam User user){
		try {
			LoginResponse response  =  new LoginResponse();
			UserDto existUser = userService.login(user.getPhone());
			
			if(existUser != null) {
				response.setUser(null);
				response.setMessage("User Already exist with is phone number!!");
				response.setJwt(null);
				return new ResponseEntity<>(response,HttpStatus.CONFLICT);
			}
			UserDto registerUser = userService.register(user);
			String token = jwtAuthentication.generateToken(registerUser.getPhone());
			
			response.setUser(registerUser);
			response.setMessage("The User register successfully");
			response.setJwt(token);
			
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
		    LoginResponse response = new LoginResponse();
		    response.setMessage("An error occurred during registration: " + e.getMessage());
		    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<String> refreshToken(@RequestParam String phone_number){
		try {
			String token = userService.refreshJwtToken(phone_number);
			return new ResponseEntity<String>(token,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
