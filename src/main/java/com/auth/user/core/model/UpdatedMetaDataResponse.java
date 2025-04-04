package com.auth.user.core.model;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatedMetaDataResponse {


	private UUID id;
	
	private String fcmToken;
	
	private String device_id;
	
	private Double app_version;
	
	private String message;
}
