package com.service.geteway.core.configuration;

import java.security.Key;

import org.springframework.stereotype.Service;

import com.service.geteway.adapter.constant.AppConstant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtAuthenticator {

	private final Key key = Keys.hmacShaKeyFor(AppConstant.SECRET_KEY.getBytes());
	
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
