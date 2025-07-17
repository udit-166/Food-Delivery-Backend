package com.notification.send.core.usecase;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

	@Override
	public boolean sendNotification(String token, String title, String body) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "key="+ AppConstant.FIREBASE_API_KEY);
			
			Map<String, Object> notification = Map.of("title", title, "body", body);
			Map<String, Object> bodyMap = new HashMap<>();
			
			bodyMap.put("to", token);
			bodyMap.put("notification", notification);
			bodyMap.put("priority", "high");
			
			HttpEntity<Map<String, Object>> entity = new HttpEntity<>(bodyMap, headers);
			
			RestTemplate restTemplate = new RestTemplate();
			
			ResponseEntity<String> response = restTemplate.postForEntity(AppConstant.FIREBASE_NOTIFICATION_API, entity, String.class);
			
			System.out.println("Notification sent: "+response.getBody());
			
			return true;
			
		}
		catch (Exception e) {
			return false;
		}
		
	}

}
