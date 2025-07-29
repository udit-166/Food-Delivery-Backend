package com.service.geteway.core.configuration;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.service.geteway.adapter.constant.AppConstant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.ws.rs.core.HttpHeaders;
import reactor.core.publisher.Mono;


@Component
public class JwtAuthFilter implements GlobalFilter , Ordered {

		private final Key key = Keys.hmacShaKeyFor(AppConstant.SECRET_KEY.getBytes());
	
		@Autowired
		private JwtAuthenticator jwtAuthentication;

		@Override
		public int getOrder() {
			return -1;
		}

		@Override
		public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
			ServerHttpRequest request = exchange.getRequest();
			
			String path = exchange.getRequest().getURI().getPath();
		    
		    if (path.startsWith("/auth/login") || path.startsWith("/auth/registerUser") || path.startsWith("/auth/refreshToken")) {
		        return chain.filter(exchange);
		    }
			
			//check authorization header
			
			if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return onError(exchange, "Missing Authorization Header", HttpStatus.UNAUTHORIZED);
			}
			
			String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
			String token = authHeader != null &&  authHeader.startsWith("Bearer ")? authHeader.substring(7) : null;
			
			if(token == null || !isValidToken(token)) {
				return onError(exchange, "Invalid Jwt  Token", HttpStatus.UNAUTHORIZED);
				
			}
			
			//if valid , proceed
			
			return chain.filter(exchange);
		}
		
		private boolean isValidToken(String token) {
			 try {
				Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
				
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		
		private Mono<Void> onError(ServerWebExchange exchange , String message, HttpStatus status){
			ServerHttpResponse response = exchange.getResponse();
			response.setStatusCode(status);
			
			byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
			
			DataBuffer buffer = response.bufferFactory().wrap(bytes);
			
			return response.writeWith(Mono.just(buffer));
		}

		
	}

