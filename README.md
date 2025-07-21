# 🛒 Order Microservice - Food Delivery App

This microservice is responsible for managing **orders**, **order items**, and **payment status tracking** in the Food Delivery App. It handles order creation, linking with restaurants and customers, and scheduled background tasks like auto-cancelling unpaid orders.

---

## 📦 Core Features

- ✅ Create and fetch customer orders
- ✅ Track payment status linked to orders
- ✅ Auto-cancel unpaid orders after a timeout
- ✅ Maintain order status: `PENDING`, `CONFIRMED`, `CANCELLED`, `DELIVERED`
- ✅ Cascade relationship with OrderItems and Payments
- ✅ Scheduled job for auto-cancellation using Spring Scheduler

---

## 📁 Tech Stack

- **Java 17**, **Spring Boot 4**
- **JPA / Hibernate**
- **PostgreSQL**
- **Lombok**, **Jakarta Persistence**
- **Spring Scheduler**
- **Redis**
- **Mapper**
- **Razorpay**

---

## 📑 Entities (Some Of Them Are)

### 🧾 Order
- `UUID id`
- `UUID customerId` (from Auth Service)
- `UUID restaurantId` (from Restaurant Service)
- `OrderStatus status`
- `BigDecimal totalPrice`
- `Boolean isActive`
- `LocalDateTime create_at`
- `LocalDateTime updated_at`
- `List<OrderItem> items`
- `List<Payment> payments`

### 💸 Payment
- `UUID id`
- `order`
- `razorpayOrderId`
- `razorpayPaymentId`
- `razorpaySignature`
- `amount`
- `currency`
- `PaymentStatus status` (`PENDING`, `SUCCESS`, `FAILED`, `EXPIRED`)
- `LocalDateTime create_at`

---

## 🔁 Scheduled Jobs

### ⏱ AutoCancelOrderScheduler
Runs every **5 minutes** to:
- Find all orders with status `PENDING`
- Check for any pending payments that exceeded the timeout (default 15 minutes)
- If so:
  - Cancel the order (`OrderStatus.CANCELLED`)
  - Mark the payment as `EXPIRED`

---

## 🔗 API Endpoints (Few Of Them Are)

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/orders` | `POST` | Create a new order |
| `/api/orders/{id}` | `GET` | Get order by ID |
| `/api/orders/customer/{customerId}` | `GET` | Get all orders for a customer |
| `/api/orders/restaurant/{restaurantId}` | `GET` | Get all orders for a restaurant |
| `/api/orders/{orderId}/status` | `PATCH` | Update order status |
| `/api/payments/order/{orderId}` | `GET` | Get all payments for a given order |

---

## 📌 Constants (Some Of Them Are)

```java
// AppConstant.java
public static final int PAYMENT_TIMEOUT_MINUTES = 15;
public static final String ORDER_ENTITY = "orders";
public static final String ORDER_ITEM_ENTITY = "order_item";
public static final String ORDER_HISTORY = "order_history";
public static final String PAYMENT = "payments";
