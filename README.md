# 🛒 Order Microservice - Food Delivery App

This microservice is responsible for managing **orders**, **order items**, **payment processing**, and **tracking payment/order statuses**.  
It connects with **Restaurant Service**, **Auth Service**, and **Payment Gateway (Razorpay)** to provide a complete order lifecycle.

---

## 📦 Core Features

- ✅ Place and fetch customer orders
- ✅ Link orders with restaurants and customers
- ✅ Track order status: `PENDING`, `CONFIRMED`, `CANCELLED`, `DELIVERED`
- ✅ Track payment status: `PENDING`, `SUCCESS`, `FAILED`, `EXPIRED`
- ✅ Auto-cancel unpaid orders after a timeout (default 15 minutes)
- ✅ Request for order cancellation from customers
- ✅ Pending review tracking
- ✅ Generate order summary
- ✅ Payment retry and refund handling
- ✅ Calculate total earnings for restaurants

---

## 📁 Tech Stack

- **Java 17**, **Spring Boot**
- **Spring Data JPA / Hibernate**
- **PostgreSQL**
- **Lombok**, **Jakarta Persistence**
- **Spring Scheduler**
- **Redis**
- **Razorpay Integration**
- **Model Mapper**

---

## 📑 Entities (Some of Them)

### 🧾 Order
```java
UUID id;
UUID customerId;
UUID restaurantId;
OrderStatus status;
BigDecimal totalPrice;
Boolean isActive;
LocalDateTime created_at;
LocalDateTime updated_at;
List<OrderItem> items;
List<Payment> payments;
```

### 💸 Payment
```java
UUID id;
Order order;
String razorpayOrderId;
String razorpayPaymentId;
String razorpaySignature;
BigDecimal amount;
String currency;
PaymentStatus status; // PENDING, SUCCESS, FAILED, EXPIRED
LocalDateTime created_at;
```

---

## 🔁 Scheduled Jobs

### ⏱ AutoCancelOrderScheduler
Runs every **5 minutes** to:
- Identify all `PENDING` orders
- If payment is not completed within **15 minutes**, the order is cancelled and payment marked as `EXPIRED`

---

## 🔗 API Endpoints

### 📌 Order APIs
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/orders/place` | Place a new order |
| `GET` | `/api/orders/{order_id}` | Get order by ID |
| `GET` | `/api/orders/customer/{customer_id}` | Get orders by customer ID |
| `GET` | `/api/orders/restaurant/{restaurant_id}` | Get orders by restaurant ID |
| `PUT` | `/api/orders/status` | Update order status |
| `PUT` | `/api/orders/cancel` | Cancel an order (Admin/Restaurant) |
| `PUT` | `/api/orders/request-cancellation` | Request order cancellation (Customer) |
| `GET` | `/api/orders/track/{order_id}` | Track order |
| `GET` | `/api/orders/summary?order_id={order_id}` | Get order summary |
| `GET` | `/api/orders/count/customer/{customer_id}` | Count orders for a customer |
| `GET` | `/api/orders/count/restaurant/{restaurant_id}` | Count orders for a restaurant |
| `GET` | `/api/orders/pending-review/{customer_id}` | Get pending reviews for customer orders |

---

### 💳 Payment APIs
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/payments/initiate` | Initiate payment for an order |
| `PUT` | `/api/payments/verify` | Verify payment (Razorpay callback) |
| `PUT` | `/api/payments/refund?payment_id={payment_id}` | Refund payment |
| `GET` | `/api/payments/order/{order_id}` | Get all payments for an order |
| `GET` | `/api/payments/success/{order_id}` | Get successful payments for an order |
| `GET` | `/api/payments/status/{status}` | Get payments by status |
| `GET` | `/api/payments/total-earning/{restaurant_id}` | Get total earnings for a restaurant |
| `POST` | `/api/payments/retry?order_id={order_id}` | Retry payment for an order |

---

## ⚙️ Constants
```java
public static final int PAYMENT_TIMEOUT_MINUTES = 15;
public static final String ORDER_ENTITY = "orders";
public static final String ORDER_ITEM_ENTITY = "order_item";
public static final String ORDER_HISTORY = "order_history";
public static final String PAYMENT = "payments";
```

---

## 📌 How to Use in Postman
1. Import the API collection into Postman.
2. Set up environment variables for:
   - `base_url`
   - `customer_id`
   - `restaurant_id`
   - `order_id`
3. Ensure Auth Service and Restaurant Service are running for dependent APIs.
4. Test using the sequence:
   - Place Order → Initiate Payment → Verify Payment → Track Order → Order Summary.

---

**Made with ❤️ for a seamless food delivery experience.**
