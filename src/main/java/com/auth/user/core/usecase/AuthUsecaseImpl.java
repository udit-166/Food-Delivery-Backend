package com.auth.user.core.usecase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.auth.user.adapter.mapper.UserMapper;
import com.auth.user.adapter.repository.UserRepository;
import com.auth.user.common.constant.AppConstants;
import com.auth.user.core.entity.User;
import com.auth.user.core.model.UserDetailsResponse;
import com.auth.user.core.model.UserDto;
import com.auth.user.core.utils.JwtAuthentication;


@Service
public class AuthUsecaseImpl implements AuthUsecase{
	
	private UserRepository userRepository;
	
	private UserMapper userMapper;
	
	private JwtAuthentication jwtAuthentication;
	
	private StringRedisTemplate redisTemplate;
	
	
	private AuthUsecaseImpl(UserRepository userRepository, JwtAuthentication jwtAuthentication,StringRedisTemplate redisTemplate) {
		this.userRepository = userRepository;
		this.jwtAuthentication = jwtAuthentication;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public UserDto login(String phoneNumber) {
		User user = userRepository.findUserByPhoneNumber(phoneNumber);
		
		UserDto loginResponse = userMapper.entityToDto(user);
		
		return loginResponse;
	}

	@Override
	public UserDto register(User user) {
		 
		User payload = new User();
		
		payload.setEmail(user.getEmail());
		payload.setPhone(user.getPhone());
		payload.setLocation(user.getLocation());
		payload.setDevice_id(user.getDevice_id());
		payload.setProfileImageUrl(user.getProfileImageUrl());
		payload.setActive(true);
		payload.setVerified(true);
		
		userRepository.save(payload);
		
		UserDto registerUser = userMapper.entityToDto(payload);
		
		return registerUser;
	}

	@Override
	public UserDto findByGoogleId(String googleId) {
		User userByGoogleId = userRepository.findByGoogleId(googleId);
		
		UserDto userByGoogleRegistration = userMapper.entityToDto(userByGoogleId);
		
		return userByGoogleRegistration;
	}

	@Override
	public String refreshToken(String phone_number) {
		String token = jwtAuthentication.generateToken(phone_number);
		return token;
	}

	@Override
	public String generateOtp(String phone_number) {
		String otp = String.format("%04d", new Random().nextInt(10000)); // 4-digit OTP
        redisTemplate.opsForValue().set(phone_number, otp, AppConstants.OTP_EXPIRY_MINUTES, TimeUnit.MINUTES);
        return otp;
	}

	@Override
	public void sendOtp(String phone_number) {
		try {
			//important step
		String otp = this.generateOtp(phone_number);
		String language = "english";
		
		String route = "otp";
		
		String myURL = "https://www.fast2sms.com/dev/bulkV2?authorization="+AppConstants.TWILIO_API_KEY+"&variables_values="+otp+"&route="+route+"&numbers="+phone_number;
		//sending get request using java
		
		System.out.println(myURL);
		
		URL url = new URL(myURL);
		
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		
		con.setRequestMethod("GET");
		
		con.setRequestProperty("User-Agent","Mozilla/5.0");
		con.setRequestProperty("cache-control", "no-cache");
		
		System.out.println("waiting..................");
		
		int code = con.getResponseCode();
		
		System.out.println("Response Code "+code);
		
		StringBuffer response = new StringBuffer();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		while(true) {
			String line = br.readLine();
			if(line==null) {
				break;
			}
			response.append(line);
		}
		System.out.println(response);
		
		
		
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
	}
	
	@Override
	public String getOtp(String phoneNumber) {
		return redisTemplate.opsForValue().get(phoneNumber);
	}

	@Override
	public boolean validateOtp(String phoneNumber, String otp) {
		String storedOtp = redisTemplate.opsForValue().get(phoneNumber);
        return storedOtp != null && storedOtp.equals(otp);
	}

	@Override
	public void deleteOtp(String phoneNumber) {
		redisTemplate.delete(phoneNumber);
		
	}

	
}
