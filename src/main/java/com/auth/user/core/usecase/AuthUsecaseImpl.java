package com.auth.user.core.usecase;


import java.util.Random;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.auth.user.adapter.repository.UserRepository;
import com.auth.user.common.constant.AppConstants;
import com.auth.user.core.entity.User;

import com.auth.user.core.utils.JwtAuthentication;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthUsecaseImpl implements AuthUsecase{

	private final UserRepository userRepository;
	
	private final JwtAuthentication jwtAuthentication;
	
	private final StringRedisTemplate redisTemplate;

	private final KafkaTemplate<String, String> kafkaTemplate;
	

	@Override
	public User login(String phoneNumber) {
		User user = userRepository.findUserByPhoneNumber(phoneNumber);
		if(user == null) {
			return null;
		}
		return user;
		
	}

	@Override
	public User register(User user) {
		 
		User payload = new User();
		
		payload.setUsername("user");
		payload.setEmail(user.getEmail());
		payload.setPhone(user.getPhone());
		payload.setLocation(user.getLocation());
		payload.setDevice_id(user.getDevice_id());
		payload.setProfileImageUrl(user.getProfileImageUrl());
		payload.setActive(true);
		payload.setVerified(true);
		
		User savedUser = userRepository.save(payload);
		System.out.printf("The saved user is::::::", savedUser);
		
		 return savedUser;
	}

	@Override
	public User findByGoogleId(String googleId) {
		User userByGoogleId = userRepository.findByGoogleId(googleId);
		
		//UserDto userByGoogleRegistration = userMapper.entityToDto(userByGoogleId);
		
		return userByGoogleId;
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
			kafkaTemplate.send("send_otp", phone_number);
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
