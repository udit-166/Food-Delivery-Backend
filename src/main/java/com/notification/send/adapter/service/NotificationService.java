package com.notification.send.adapter.service;

import com.notification.send.adapter.models.EmailNotificationDTO;


public interface NotificationService {

	public void sendEmail(EmailNotificationDTO request);
	
	public String generateOtp(String phone_number);
	
	public void sendOtp(String phone_number);
}
