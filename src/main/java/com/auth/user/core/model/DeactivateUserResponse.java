package com.auth.user.core.model;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeactivateUserResponse {

	
	private UUID id;
	
	private boolean active;
	
	private String message;
}
