package com.notification.send.adapter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notification.send.adapter.constant.AppConstant;
import com.notification.send.adapter.models.EmailNotificationDTO;
import com.notification.send.adapter.service.NotificationService;

@RestController
@RequestMapping(AppConstant.NOTIFICATION_CONTROLLER)
public class NotificationController {

	@Autowired
	private NotificationService notificationService;
	
	@PostMapping(AppConstant.SEND_EMAIL_NOTIFICATION)
	ResponseEntity<String> sendNotificationViaEmail(@RequestParam EmailNotificationDTO request){
		try {
			notificationService.sendEmail(request);
			return new ResponseEntity<>("The email is send to respective email Id. Kindly check it", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Something went wrong.....", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
