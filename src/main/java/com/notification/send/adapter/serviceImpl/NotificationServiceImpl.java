package com.notification.send.adapter.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notification.send.adapter.models.EmailNotificationDTO;
import com.notification.send.adapter.service.NotificationService;
import com.notification.send.core.usecase.NotificationUsecase;

@Service
public class NotificationServiceImpl implements NotificationService{
	
	@Autowired
	private NotificationUsecase notificationUsecase;

	@Override
	public void sendEmail(EmailNotificationDTO request) {
		
		String message = request.getMessage();
		String subject = request.getSubject();
		String to = request.getTo();
		notificationUsecase.sendEmail(subject, message, to);
	}

}
