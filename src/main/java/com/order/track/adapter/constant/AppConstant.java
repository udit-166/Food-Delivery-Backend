package com.order.track.adapter.constant;

public class AppConstant {
	
	
	public static final String ORDER_ENTITY = "orders";
	
	public static final String ORDER_ITEM_ENTITY = "order_item";
	
	public static final String PLACE_ORDER = "/place_order";
	
	public static final String GET_ORDER_BY_ID = "/get_order_by_id/{order_id}";
	
	public static final String GET_ORDER_BY_CUSTOMER_ID = "/get_order_by_customer_id/{customer_id}";
	
	public static final String GET_ORDER_BY_RESTAURANT_ID = "/get_order_by_restaurant_id/{restaurant_id}";
	
	public static final String UPDATE_ORDER_STATUS = "/update_order_status";
	
	public static final String CANCEL_ORDER = "/cancel_order";
	
	public static final String REQUEST_FOR_CANCELLATION_OF_ORDER = "/request_for_cancellation";
	
	public static final String TRACK_YOUR_ORDER = "/track_order/{order_id}";
}
