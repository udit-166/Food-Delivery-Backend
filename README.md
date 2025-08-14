# 📢 Notification Service - Food Delivery App

This microservice is responsible for **sending notifications** to users and restaurant owners via **Email**, **SMS OTP**, and **Push Notifications (FCM)**.  
It also consumes events from Kafka (Order & Payment events) and sends relevant notifications in real-time.

---

## 📦 Core Features

- 📧 **Email Notifications** (Order updates, Payment updates)
- 🔢 **OTP Generation & Sending** (SMS-based verification)
- 📱 **Push Notifications** via **Firebase Cloud Messaging (FCM)**
- 📬 **Event-Driven Notifications** based on Kafka events:
  - Order Placed
  - Order Assigned to Delivery Person
  - Order Dispatched
  - Order Delivered
  - Payment Failed
  - Payment Success

---

## 📁 Tech Stack

- **Java 17**, **Spring Boot**
- **Spring Kafka** (Event consumption)
- **JavaMailSender** (Email)
- **Firebase Admin SDK** (Push Notifications)
- **Twilio / SMS Gateway** (OTP Sending)
- **Lombok**, **Jakarta Mail**
- **Docker** (Containerization)

---

## 📂 Project Structure

```
notification-service
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.notification.send
│   │   │       ├── adapter
│   │   │       │   ├── controller        # REST API Endpoints
│   │   │       │   ├── models            # DTOs for Email, FCM, OTP, etc.
│   │   │       │   └── service           # NotificationService interface
│   │   │       └── serviceImpl           # Implementation of NotificationService
│   │   └── resources
│   │       ├── application.yml           # Configuration (SMTP, FCM, Kafka, SMS)
│   │       └── templates                 # Email templates
├── Dockerfile
├── README.md
└── pom.xml
```

---

## 📜 Service Interface

```java
public interface NotificationService {
    void sendEmail(EmailNotificationDTO request);
    String generateOtp(String phone_number);
    void sendOtp(String phone_number);
    boolean sendNotification(FcmNotification notificationData);
    void handleOrderPlaced(HandleNotificationRequest payload);
    void handleOrderDispatched(HandleNotificationRequest payload);
    void handleOrderAssignedToDeliveryPerson(HandleNotificationRequest payload);
    void handleOrderDilveredNotification(HandleNotificationRequest payload);
    void handlePaymentFailedNotification(HandleNotificationRequest payload);
    void handlePaymentSuccessNotification(HandleNotificationRequest payload);
}
```

---

## 🔗 Kafka Event Handlers

| Event Name | Method |
|------------|--------|
| `order.placed` | `handleOrderPlaced()` |
| `order.assigned` | `handleOrderAssignedToDeliveryPerson()` |
| `order.dispatched` | `handleOrderDispatched()` |
| `order.delivered` | `handleOrderDilveredNotification()` |
| `payment.failed` | `handlePaymentFailedNotification()` |
| `payment.success` | `handlePaymentSuccessNotification()` |

---

---

## 📌 Environment Variables

| Variable | Description |
|----------|-------------|
| `SMTP_HOST` | SMTP server hostname |
| `SMTP_PORT` | SMTP server port |
| `SMTP_USERNAME` | Email username |
| `SMTP_PASSWORD` | Email password |
| `FCM_CREDENTIALS_PATH` | Path to Firebase credentials JSON |
| `TWILIO_ACCOUNT_SID` | Twilio account SID |
| `TWILIO_AUTH_TOKEN` | Twilio auth token |
| `TWILIO_PHONE_NUMBER` | Sender phone number for OTP |
| `KAFKA_BROKER` | Kafka broker address |

---

## 🚀 Running the Service

```bash
# Build and Run
mvn clean install
mvn spring-boot:run

# Or using Docker
docker build -t notification-service .
docker run -p 8084:8084 notification-service
```

---

**Made with ❤️ for fast and reliable notifications in Food Delivery App**
