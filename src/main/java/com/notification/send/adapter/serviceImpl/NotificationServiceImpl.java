package com.notification.send.adapter.serviceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.notification.send.adapter.constant.AppConstant;
import com.notification.send.adapter.models.EmailNotificationDTO;
import com.notification.send.adapter.models.FcmNotification;
import com.notification.send.adapter.models.HandleOrderRequest;
import com.notification.send.adapter.service.NotificationService;
import com.notification.send.core.usecase.NotificationUsecase;

@Service
public class NotificationServiceImpl implements NotificationService{
	
	@Autowired
	private NotificationUsecase notificationUsecase;
	
	@Autowired
	private StringRedisTemplate redisTemplate;

	@Override
	@KafkaListener(topics = "send_email_topic", groupId = "notification-group", containerFactory = "kafkaListenerContainerFactory")
	public void sendEmail(EmailNotificationDTO request) {
		
		String message = request.getMessage();
		String subject = request.getSubject();
		String to = request.getTo();
		notificationUsecase.sendEmail(subject, message, to);
	}

	@Override
	public String generateOtp(String phone_number) {
		String otp = String.format("%04d", new Random().nextInt(10000)); // 4-digit OTP
        redisTemplate.opsForValue().set(phone_number, otp, AppConstant.OTP_EXPIRY_MINUTES, TimeUnit.MINUTES);
        return otp;
	}

	@Override
	@KafkaListener(topics = "send_otp", groupId = "notification-group", containerFactory = "kafkaListenerContainerFactory")
	public void sendOtp(String phone_number) {
		try {
			//important step
		String otp = this.generateOtp(phone_number);
		String language = "english";
		
		String route = "otp";
		
		String myURL = "https://www.fast2sms.com/dev/bulkV2?authorization="+AppConstant.TWILIO_API_KEY+"&variables_values="+otp+"&route="+route+"&numbers="+phone_number;
		//sending get request using java
		
		System.out.println(myURL);
		
		URL url = new URL(myURL);
		
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		
		con.setRequestMethod("GET");
		
		con.setRequestProperty("User-Agent","Mozilla/5.0");
		con.setRequestProperty("cache-control", "no-cache");
		
		System.out.println("waiting..................");
		
		int code = con.getResponseCode();
		
		System.out.println("Response Code "+code);
		
		StringBuffer response = new StringBuffer();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		while(true) {
			String line = br.readLine();
			if(line==null) {
				break;
			}
			response.append(line);
		}
		System.out.println(response);
		
		
		
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
	}

	@Override
	@KafkaListener(topics = "send_notification", groupId = "notification-group", containerFactory = "kafkaListenerNotificationContainerFactory")
	public boolean sendNotification(FcmNotification notificationData) {
		String to = notificationData.getTo();
		String title = notificationData.getTitle();
		String body = notificationData.getBody();
		boolean result = notificationUsecase.sendNotification(to, title, body);
		return result;
	}

	@Override
	@KafkaListener(topics = "handle_order_placed", groupId = "notification-group", containerFactory = "kafkaListenerNotificationContainerFactory")
	public void handleOrderPlaced(HandleOrderRequest payload) {
		try {
			String title = "Order Placed";
			String body = "Your Order with orderId "+ payload.getOrderId()+" is placed.";
			notificationUsecase.sendNotification(payload.getFcmToken(), title, body);
			notificationUsecase.sendEmail("Order Placed",body, payload.getEmail());
			
		} catch (Exception e) {
			System.out.println("The error occur while sending the notification for order placed!!");
		}
	}

	@Override
	@KafkaListener(topics = "handle_order_dispatched", groupId = "notification-group", containerFactory = "kafkaListenerNotificationContainerFactory")
	public void handleOrderDispatched(HandleOrderRequest payload) {
		try {
			String title = "Order Dispatched";
			String body = "Your Order with orderId "+ payload.getOrderId()+" is dispatched.";
			notificationUsecase.sendNotification(payload.getFcmToken(), title, body);
			
		} catch (Exception e) {
			System.out.println("The error occur while sending the notification for order dispatched!!");
		}
		
	}

	@Override
	@KafkaListener(topics = "handle_order_assigned_to_delivery_person", groupId = "notification-group", containerFactory = "kafkaListenerNotificationContainerFactory")
	public void handleOrderAssignedToDeliveryPerson(HandleOrderRequest payload) {
		try {
			String title = "Delivery Person Is Here";
			String body = "The delivery person is assigned for your order and will deliver in 10 minutes.";
			notificationUsecase.sendNotification(payload.getFcmToken(), title, body);
			
		} catch (Exception e) {
			System.out.println("The error occur while sending the notification for order assigned to delivery person.");
		}
		
	}

	@Override
	@KafkaListener(topics = "handle_order_delivered", groupId = "notification-group", containerFactory = "kafkaListenerNotificationContainerFactory")
	public void handleOrderDilveredNotification(HandleOrderRequest payload) {
		try {
			String title = "Order Delivered";
			String body = "Your order has been delivered to you. Please rate us and share your food taste and expirence with us/.";
			notificationUsecase.sendNotification(payload.getFcmToken(), title, body);
			
		} catch (Exception e) {
			System.out.println("The error occur while sending the notification for order delivered!!");
		}
		
	}

	@Override
	@KafkaListener(topics = "handle_payment_failed", groupId = "notification-group", containerFactory = "kafkaListenerNotificationContainerFactory")
	public void handlePaymentFailedNotification(HandleOrderRequest payload) {
		try {
			String title = "Order Payment Failed!";
			String body = "Your payment is failed. Your money is not debited. Please try again to confirmed the order.";
			notificationUsecase.sendNotification(payload.getFcmToken(), title, body);
			
		} catch (Exception e) {
			System.out.println("The error occur while sending the notification for order payment failed!!");
		}
		
	}

	@Override
	@KafkaListener(topics = "handle_payment_success", groupId = "notification-group", containerFactory = "kafkaListenerNotificationContainerFactory")
	public void handlePaymentSuccessNotification(HandleOrderRequest payload) {
		try {
			String title = "Order Payment Successfully Done!";
			String body = "Your payment is successfully done.";
			notificationUsecase.sendNotification(payload.getFcmToken(), title, body);
			
		} catch (Exception e) {
			System.out.println("The error occur while sending the notification for order payment done successfully!!");
		}
		
	}

//	@Override
//	public void handlePaymentPendingNotification(HandleOrderRequest payload) {
//		// TODO Auto-generated method stub
//		
//	}
	

}
