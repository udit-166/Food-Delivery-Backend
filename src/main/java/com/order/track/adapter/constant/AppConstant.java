package com.order.track.adapter.constant;

public class AppConstant {
	
	
	public static final String ORDER_ENTITY = "orders";
	
	public static final String ORDER_ITEM_ENTITY = "order_item";
	
	public static final String ORDER_HISTORY = "order_history";
	
	public static final String PAYMENT = "payments";
	
	public static final String PLACE_ORDER = "/place_order";
	
	public static final String GET_ORDER_BY_ID = "/get_order_by_id/{order_id}";
	
	public static final String GET_ORDER_BY_CUSTOMER_ID = "/get_order_by_customer_id/{customer_id}";
	
	public static final String GET_ORDER_BY_RESTAURANT_ID = "/get_order_by_restaurant_id/{restaurant_id}";
	
	public static final String UPDATE_ORDER_STATUS = "/update_order_status";
	
	public static final String CANCEL_ORDER = "/cancel_order";
	
	public static final String REQUEST_FOR_CANCELLATION_OF_ORDER = "/request_for_cancellation";
	
	public static final String TRACK_YOUR_ORDER = "/track_order/{order_id}";
	
	public static final String ORDER_SUMMARY = "/order_summary";
	
	public static final String RAZORPAY_SECRET = "UHzyRiKK2A3LloXMTefYU3Ad";
	
	public static final String RAZORPAY_KEY = "rzp_test_V1LSdhEUVwNt9B";
	
	public static final String GET_COUNT_OF_ORDER_BY_CUSTOMER_ID = "/getCountOfOrderByCustomerId/{customer_id}";
	
	public static final String GET_COUNT_OF_ORDER_BY_RESTAURANT_ID = "/getCountOfOrderByrestaurantId/{restaurant_id}";	
	
	public static final String INITIATE_PAYMENT = "/initiate_payment";
	
	public static final String VERIFY_PAYMENT = "/verify_payment";
	
	public static final String REFUND_PAYMENT = "/refund_payment";
	
	public static final String PAYMENTS_BY_ORDER_ID = "/getListOfPayment/{orderId}";
	
	public static final String SUCCESS_PAYMENT_BY_ORDER_ID = "/getSuccessPayment/{orderId}";
	
	public static final String PAYMENTS_BY_STATUS = "/getPaymentByStatus/{status}";
	
	public static final String GET_TOTAL_EARNING_OF_RESTAURANT = "/getTotalEarningsForRestaurant/{restaurant_id}";
	
	public static final String PAYMENT_CONTOLLER = "/api/payment";
	
	public static final String ORDER_CONTROLLER = "/api/order";
	
	public static final String RETRY_PAYMENT = "/retry_payment";
	
	public static final int PAYMENT_TIMEOUT_MINUTES = 15;
}
