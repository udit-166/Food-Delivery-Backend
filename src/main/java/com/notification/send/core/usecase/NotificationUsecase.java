package com.notification.send.core.usecase;

public interface NotificationUsecase {
	
	public void sendEmail(String subject, String message, String to);

}
