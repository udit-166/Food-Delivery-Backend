package com.delivery.cart.adapter.constant;

public class AppConstant {

	public static final String PREFIX = "cart:";
	
	public static final String ADD_CART = "/add";
	
	public static final String GET_CART = "/getCartDetails";
	
	public static final String REMOVE_CARTITEMS = "/removeCartItem";
	
	public static final String CLEAR_CART = "/clearCart/{userId}";
	
	public static final String CART_CONTROLLER = "/api/cart";
	
	public static final String DELIVERY_TABLE = "deliveries";
	
	public static final String REVIEW_TABLE = "reviews";
	
	public static final String VEHICLE_DETAILS = "vehicle_details";
	
	public static final String DELIVERY_CONTROLLER = "/api/delivery";
	
	public static final String REGISTER_VEHICLE = "/register_vehicle";
	
	public static final String GOOGLE_MAP_API_KEY = "AIzaSyCRhyWhEvsK7tTUqjxG7s9kHcACPw8cBdg";
	
	public static final int INITIAL_RADIUS_KM = 3;
	
	public static final int MAX_RADIUS_KM = 20;
	
	public static final int MAX_ATTEMPTS = 3;
	
	public static final int MAX_CANDIDATE_LIMITS = 10;
	
	public static final int FIXED_DELAY_MS = 120000;
	
	public static final String GEO_KEY = "delivery:partners:geo";
	
    public static final String PARTNER_STATUS_PREFIX = "delivery:partner:%s:status";
    
    public static final String ORDER_LOCK_PREFIX = "delivery:order:%s:lock";
    
    public static final String PARTNER_NAME_KEY = "delivery:partner:%s:name";
    
    public static final String RETRY_ORDER_KEY = "delivery:orders:pending";
    
    public static final String PARTNER_LOCK_KEY = "delivery:partner:%s:assign_lock";
	
    public static final String ORDER_ATTEMPT_PREFIX = "delivery:order:%s:attempts";
    
    public static final String RETRY_ORDER_FORCED_KEY = "delivery:orders:forced";
    
    public static final String ASSIGN_DELIVERY_PERSON = "/assign_delivery_person";
    
    public static final String UPDATE_DELIVERY_STATUS = "/updateDeliveryStatus";
    
    public static final String GET_DELIVERY_DETAILS = "/getDeliveryDetailsById/{delivery_id}";
    
    public static final String SUBMIT_REVIEWS = "/submitReview";
}
