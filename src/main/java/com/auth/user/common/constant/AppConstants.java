package com.auth.user.common.constant;


public class AppConstants {
	
	public static final String FDAUSER = "fda_user";
	
	public static final String FDAADDRESS = "fda_address";
	
	public static final String SECRET_KEY = "hjhjvhabjkxnsajbashiasnkasmklnskjabaskmkdadanc,jnkahdbjbhjbhjdbjjd";
	
	public static final String AUTHORIZATION = "Authorization";
	
	public static final long EXPIRATION_TIME = 1000*60*60*24;
	
	public static final String CLIENT_ID = "326597451983-4heq0h4d94pcnvku860ie4bfjmdfgtag.apps.googleusercontent.com";
	
	public static final long OTP_EXPIRY_MINUTES = 5;
	
	public static final String TWILIO_API_KEY = "EmVTwn5bucyZ7rQkp2aNC3IYLxeGSBXRhi6qzlDP8d1HjUf4AtxpQAvlgukrX1FBUSwqfaWiPmLs5heD";
	
	public static final String TWILIO_SEND_ID = "FSTSMS";
	
	public static final String USER_CONTROLLER = "/api/users";
	
	public static final String GET_PROFILE_BY_PHONE_NUMBER = "/getProfileDetails/phone/{phone_number}";
	
	public static final String GET_PROFILE_BY_EMAIL = "/getProfileDetails/email/{email}";
	
	public static final String UPDATE_PROFILE = "/updateProfile";
	
	public static final String GET_ROLE = "/getRoleOfUser";
	
	public static final String GET_CURRENT_ADDRESS = "/getCurrentAddress";
	
	public static final String GET_ALL_ADDRESS_OF_USER = "/getListOfAddress";
	
	public static final String SAVE_ADDRESS = "/saveAddress";
	
	public static final String UPDATE_ADDRESS = "/updateAddress";
	
	public static final String UPDATE_USER_META_DATA = "/updateUserMetaData";
	
	public static final String DELETE_ACCOUNT = "/deleteAccount";
	
	public static final String AUTH_CONTROLLER = "/api/auth";
	
	public static final String LOGIN = "/login";
	
	public static final String REGISTER_USER = "/registerUser";
	
	public static final String REFRESH_TOKEN = "/refreshToken";
	
	public static final String SEND_OTP = "/sendOtp/{phone_number}";
	
	public static final String VERIFY_OTP = "/verifyOtp";
	
	public static final String JWT_HEADER = "Authorization";

}
