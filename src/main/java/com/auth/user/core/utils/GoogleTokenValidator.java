package com.auth.user.core.utils;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Component;

import com.auth.user.common.constant.AppConstants;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

@Component
public class GoogleTokenValidator {

	private static final String CLIENT_ID = AppConstants.CLIENT_ID;

	public GoogleIdToken.Payload verifyGoogleToken(String idTokenString) throws IOException, GeneralSecurityException{
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
				new NetHttpTransport(), new GsonFactory()).setAudience(java.util.Collections.singletonList(CLIENT_ID))
				.build();
		
		GoogleIdToken idToken = verifier.verify(idTokenString);
		return (idToken != null) ? idToken.getPayload():null;
	}
}
