package com.notification.send.adapter.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.notification.send.core.entity.EmailNotification;
import com.notification.send.core.repository.EmailNotificationRepo;


@Repository
public class EmailNotificationRepositoriesImpl implements EmailNotificationRepositories{

	@Autowired
	private EmailNotificationRepo emailNotificationRepo;
	
	@Override
	public EmailNotification save(EmailNotification email_notification) {
		
		return emailNotificationRepo.save(email_notification);
	}

	
}
