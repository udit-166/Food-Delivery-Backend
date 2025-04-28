package com.notification.send.core.usecase;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notification.send.adapter.constant.AppConstant;
import com.notification.send.adapter.repositories.EmailNotificationRepositories;
import com.notification.send.core.entity.EmailNotification;

@Service
public class notificationUsecaseImpl implements NotificationUsecase{
	
	@Autowired
	private EmailNotificationRepositories emailNotificationRepositories;

	@Override
	public void sendEmail(String subject, String message, String to) {
		String from  = AppConstant.EMAIL_ADDRESS;
		// Variable for sending the message in the app
		
		String host = "smtp.gmail.com";
		//get the system properties
		
		Properties properties = System.getProperties();
		System.out.println("PROPERTIES "+properties);
		// setting importing information to properties object
		
		//host set
		
		properties.put("mail.smtp.host",host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
		
		// to get the session object
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(AppConstant.EMAIL_ADDRESS,AppConstant.EMAIL_PASSWORD);
			}
		});
		session.setDebug(true);
		
		//Step 2:- compose the message[text,multimedia]
		
		MimeMessage m = new MimeMessage(session);
		//from email
		try {
		m.setFrom(new InternetAddress(from));
		
		// adding recipient to message
		
		m.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
		
		// adding subject to message
		
		m.setSubject(subject);
		// add text to message
		
		//m.setText(message);
		
		m.setContent(message,"text/html");
		
		
		
		//send
		// Step 3: send the message using Transport class
		
		Transport.send(m);
		
		EmailNotification notification = new EmailNotification();
		
		notification.setSend_from(from);
		notification.setMessage(message);
		notification.setSend_to(to);
		emailNotificationRepositories.save(notification);
		
		System.out.println("send success................");
		}
		catch (Exception e) {
			
			System.out.println(e);
		}
	}

}
