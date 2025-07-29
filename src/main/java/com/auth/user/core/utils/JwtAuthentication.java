package com.auth.user.core.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.auth.user.common.constant.AppConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtAuthentication {

	private final Key key = Keys.hmacShaKeyFor(AppConstants.SECRET_KEY.getBytes());
	
		public String generateToken(String phone) {
			Map<String, Object> claims = new HashMap<>();
			claims.put("Phone", phone);
			return Jwts.builder().
					setClaims(claims)
					.setSubject(phone)
					.setIssuer("auth-service")
					.setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + AppConstants.EXPIRATION_TIME))
					.signWith(key, SignatureAlgorithm.HS256)
					.compact();
		
	}
		private Claims getClaims(String token) {
	        return Jwts.parser()
	                   .setSigningKey(key)
	                   .build()
	                   .parseClaimsJws(token)
	                   .getBody();
	    }
		public String extractUsername(String token) {
	        return getClaims(token).getSubject(); // phone number was set as subject
	    }
		public boolean isTokenValid(String token) {
	        try {
	            getClaims(token);
	            return true;
	        } catch (JwtException | IllegalArgumentException e) {
	            return false;
	        }
	    }
}
