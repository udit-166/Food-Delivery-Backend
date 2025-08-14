package com.notification.send.adapter.service;

import com.notification.send.adapter.models.EmailNotificationDTO;
import com.notification.send.adapter.models.FcmNotification;
import com.notification.send.adapter.models.HandleNotificationRequest;


public interface NotificationService {

	public void sendEmail(EmailNotificationDTO request);
	
	public String generateOtp(String phone_number);
	
	public void sendOtp(String phone_number);
	
	public boolean sendNotification(FcmNotification notificartionData);
	
	public void handleOrderPlaced(HandleNotificationRequest payload);
	
	
	public void handleOrderDispatched(HandleNotificationRequest payload);
	
	public void handleOrderAssignedToDeliveryPerson(HandleNotificationRequest payload);
	
	public void handleOrderDilveredNotification(HandleNotificationRequest payload);
	
	public void handlePaymentFailedNotification(HandleNotificationRequest payload);
	
	public void handlePaymentSuccessNotification(HandleNotificationRequest payload);
	
//	public void handlePaymentPendingNotification(HandleOrderRequest payload);
	
	
}
