package com.auth.user.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleResponse {

	private Role role;
	
	private String message;
	
}
