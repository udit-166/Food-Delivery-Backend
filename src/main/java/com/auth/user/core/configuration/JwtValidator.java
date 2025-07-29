package com.auth.user.core.configuration;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth.user.common.constant.AppConstants;
import com.auth.user.core.utils.JwtAuthentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtValidator extends OncePerRequestFilter{
	
	@Autowired
	private JwtAuthentication jwtAuthentication;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader(AppConstants.JWT_HEADER);
		
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			
			String phoneNumber = null;
			try {
				phoneNumber = jwtAuthentication.extractUsername(token);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(phoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(phoneNumber, null, Collections.emptyList());
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		filterChain.doFilter(request, response);
		
	}

}
