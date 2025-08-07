package com.food.restaurant.adapter.model;

import org.springframework.http.HttpStatus;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusCode {

	private int code;
	
	private String description;
	
	public static StatusCode of(HttpStatus httpStatus) {
		return new StatusCode(httpStatus.value(), httpStatus.getReasonPhrase());
	}
}
