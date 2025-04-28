package com.notification.send.adapter.service;

import com.notification.send.adapter.models.EmailNotificationDTO;


public interface NotificationService {

	public void sendEmail(EmailNotificationDTO request);
}
