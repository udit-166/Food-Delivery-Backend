package com.auth.user.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.auth.user.adapter.service.AuthService;
import com.auth.user.common.constant.AppConstants;
import com.auth.user.core.entity.User;
import com.auth.user.core.model.GenericResponse;
import com.auth.user.core.model.Location;
import com.auth.user.core.model.LoginResponse;
import com.auth.user.core.model.RefreshTokenRequestDto;
import com.auth.user.core.model.RefreshTokenResponse;
import com.auth.user.core.model.StatusCode;
import com.auth.user.core.model.UserDto;
import com.auth.user.core.model.VerifyOtpRequestDto;
import com.auth.user.core.model.otpResponse;
import com.auth.user.core.utils.GoogleTokenValidator;
import com.auth.user.core.utils.JwtAuthentication;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

@RestController
@RequestMapping(AppConstants.AUTH_CONTROLLER)
public class AuthController {
	
	private final JwtAuthentication jwtAuthentication ;
	
	private final GoogleTokenValidator googleTokenValidator;
	
	
	@Autowired
	private AuthService authService;
	
	
	public AuthController(JwtAuthentication jwtAuthentication, GoogleTokenValidator googleTokenValidator) {
		this.jwtAuthentication = jwtAuthentication;
		this.googleTokenValidator = googleTokenValidator;
	}
	
	@PostMapping(AppConstants.LOGIN)
	public GenericResponse<LoginResponse> login(@RequestParam(required = false) String phoneNumber, @RequestParam(required = false) String oauthToken, @RequestParam(required=false) Location location){

		try {
			LoginResponse response = new LoginResponse();
			if(oauthToken != null) {
				//google sign-in login flow
				Payload payload = googleTokenValidator.verifyGoogleToken(oauthToken);
				if(payload == null ) {
					return new GenericResponse<>(
							"Invalid Google login",
							StatusCode.of(HttpStatus.UNAUTHORIZED),
							null
						);
				}
				String email = payload.getEmail();
				String googleId = payload.getSubject();
				
				UserDto user = authService.findByGoogleId(googleId);
				
				if(user == null) {
					User userToRegister = new User();
					userToRegister.setUsername(payload.get("name").toString());
					userToRegister.setActive(true);
					userToRegister.setEmail(email);
					userToRegister.setGoogleId(googleId);
					userToRegister.setLocation(location);
					userToRegister.setVerified(true);
					
					UserDto registerUser = authService.register(userToRegister);
					String jwtToken = "Bearer "+jwtAuthentication.generateToken(registerUser.getPhone());
					
					response.setUser(registerUser);
					response.setJwt(jwtToken);
					return new GenericResponse<>("User login with google successfully!!", StatusCode.of(HttpStatus.OK), response);
				}
				
				// generating the jwt token for exisiting google user
				
				String jwtToken = "Bearer "+jwtAuthentication.generateToken(user.getEmail());
				response.setUser(user);
				response.setJwt(jwtToken);
				
				return new GenericResponse<>("user login with email successfully!!", StatusCode.of(HttpStatus.OK), response);
			}
		
		if(phoneNumber != null) {
			UserDto user = authService.login(phoneNumber);
			
			if(user!=null) {
				String token = "Bearer "+jwtAuthentication.generateToken(phoneNumber);
				response.setUser(user);
				response.setJwt(token);
				
				return new GenericResponse<>("User login with phone number successfully!!", StatusCode.of(HttpStatus.OK), response);
			}
			else {
				return new GenericResponse<>("User didn't find with this phone number", StatusCode.of(HttpStatus.NOT_FOUND), response);
			}
		}
			return new GenericResponse<>("Either phoneNumber or googleToken is required", StatusCode.of(HttpStatus.OK), response);
		}
		catch (Exception e) {
		    return new GenericResponse<>("An error occurred during login: " + e.getMessage(), StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PostMapping(AppConstants.REGISTER_USER)
	public GenericResponse<LoginResponse> registerUser(@RequestBody User user){
		try {
			LoginResponse response  =  new LoginResponse();
			UserDto existUser = authService.login(user.getPhone());
			
			if(existUser != null) {
				response.setUser(null);
				response.setJwt(null);
				return new GenericResponse<>("User Already exist with phone number", StatusCode.of(HttpStatus.ALREADY_REPORTED), response);
			}
			UserDto registerUser = authService.register(user);
			String token = "Bearer "+jwtAuthentication.generateToken(registerUser.getPhone());
			
			response.setUser(registerUser);
			response.setJwt(token);
			
			return new GenericResponse<>("User login register successfully!!", StatusCode.of(HttpStatus.OK), response);
			
		} catch (Exception e) {
			e.printStackTrace();

		    return new GenericResponse<>("An error occurred during registration: \" + e.getMessage()", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PostMapping(AppConstants.REFRESH_TOKEN)
	public GenericResponse<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequestDto request){
		try {
			RefreshTokenResponse token = authService.refreshJwtToken(request.getPhone_number());
			return new GenericResponse<>("Refresh token generated successfully!!", StatusCode.of(HttpStatus.OK), token);
		} catch (Exception e) {
			return new GenericResponse<>("something went wrong!!", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@GetMapping(AppConstants.SEND_OTP)
	public GenericResponse<String> sendOtp(@PathVariable String phone_number){
		try {
			authService.sendOtp(phone_number);
			return new GenericResponse<>("The otp send successfully!!", StatusCode.of(HttpStatus.OK), null);
		} catch (Exception e) {
			return new GenericResponse<>("Something went wrong", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PostMapping(AppConstants.VERIFY_OTP)
	public GenericResponse<String> verifyOtp(@RequestBody VerifyOtpRequestDto request){
		try {
			if(request.getPhone_number() == null || request.getOtp() == null) {
				return new GenericResponse<>("The parameters are missing!",StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			
			if(!authService.validateOtp(request.getPhone_number(), request.getOtp())) {
				return new GenericResponse<>("The otp is incorrect or expired. Click on resend to get new OTP",StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			return new GenericResponse<>("The phone_number is verified successfully!!", StatusCode.of(HttpStatus.OK), null);
		} catch (Exception e) {

			e.printStackTrace();
			return new GenericResponse<>("The service is not running.", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
}
