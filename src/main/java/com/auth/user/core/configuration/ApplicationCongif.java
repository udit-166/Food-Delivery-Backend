package com.auth.user.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class ApplicationCongif {
	
	@Autowired
	private JwtValidator jwtValidator;
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				).authorizeHttpRequests(auth->auth
						.requestMatchers("/api/auth/**","/api/users/**").permitAll()
						.anyRequest().authenticated()
					)
		.csrf(csrf -> csrf.disable())
		.httpBasic(httpBasic -> httpBasic.disable());
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
