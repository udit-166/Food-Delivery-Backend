package com.notification.send.adapter.service;

import com.notification.send.adapter.models.EmailNotificationDTO;
import com.notification.send.adapter.models.FcmNotification;
import com.notification.send.adapter.models.HandleOrderRequest;


public interface NotificationService {

	public void sendEmail(EmailNotificationDTO request);
	
	public String generateOtp(String phone_number);
	
	public void sendOtp(String phone_number);
	
	public boolean sendNotification(FcmNotification notificartionData);
	
	public void handleOrderPlaced(HandleOrderRequest payload);
	
	
	public void handleOrderDispatched(HandleOrderRequest payload);
	
	public void handleOrderAssignedToDeliveryPerson(HandleOrderRequest payload);
	
	public void handleOrderDilveredNotification(HandleOrderRequest payload);
	
	public void handlePaymentFailedNotification(HandleOrderRequest payload);
	
	public void handlePaymentSuccessNotification(HandleOrderRequest payload);
	
//	public void handlePaymentPendingNotification(HandleOrderRequest payload);
	
	
}
