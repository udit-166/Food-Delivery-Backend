# 🚚 Delivery & Cart Service - Food Delivery App

This microservice handles **Cart Management** and **Delivery Partner Operations** for the food delivery system.

---

## 📦 Tech Stack
- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Spring Cloud OpenFeign
- Redis (Optional)
- Docker

---

## 📌 API Reference

### 🛒 Cart Management

| API | Method | Endpoint | Description | Request Body | Response Example |
|-----|--------|----------|-------------|--------------|------------------|
| **Add to Cart** | POST | `/api/cart/add` | Adds an item or updates an existing item in the user's cart. | ```json { "userId": "uuid", "restaurantId": "uuid", "restaurantName": "KFC", "restaurantUrl": "https://img.jpg", "item": [{ "foodItemId": "uuid", "quantity": 2, "price": 199.99 }], "totalPrice": 399.98 } ``` | ```json { "message": "Item added to cart", "statusCode": 200 } ``` |
| **Get Cart** | GET | `/api/cart?userId={uuid}` | Fetches the current cart for the user. | N/A | ```json { "message": "Cart details fetched successfully!!", "statusCode": 200, "data": { "userId": "uuid", "restaurantId": "uuid", "items": [{ "foodItemId": "uuid", "quantity": 2, "price": 199.99 }], "totalPrice": 399.98 } } ``` |
| **Remove Cart Item** | PUT | `/api/cart/remove` | Removes a specific item from the cart. | ```json { "userId": "uuid", "foodItemId": "uuid" } ``` | ```json { "message": "Cart item has been removed successfully!!", "statusCode": 202 } ``` |
| **Clear Cart** | DELETE | `/api/cart/clear/{userId}` | Clears all items in the cart for a user. | N/A | ```json { "message": "Cart is cleared successfully!!", "statusCode": 200 } ``` |

---

### 🚴 Delivery Management

| API | Method | Endpoint | Description | Request Body | Response Example |
|-----|--------|----------|-------------|--------------|------------------|
| **Register Vehicle Details** | POST | `/api/delivery/register-vehicle` | Registers delivery partner's vehicle information. | ```json { "partnerId": "uuid", "vehicleType": "Bike", "vehicleNumber": "MH-12-AB-1234", "licenseNumber": "DL123456789" } ``` | ```json { "message": "Data saved successfully!!", "statusCode": 200, "data": { "partnerId": "uuid", "vehicleType": "Bike" } } ``` |
| **Assign Delivery Person** | POST | `/api/delivery/assign` | Assigns a delivery partner to an order. | ```json { "orderId": "uuid", "partnerId": "uuid" } ``` | ```json { "message": "Delivery Person Assigned", "statusCode": 200 } ``` |
| **Update Delivery Status** | PATCH | `/api/delivery/update-status?delivery_id={uuid}` | Updates the current delivery status (e.g., Dispatched, Delivered). | N/A | ```json { "message": "Update the delivery status Successfully!!", "statusCode": 200 } ``` |
| **Get Delivery Details** | GET | `/api/delivery/details/{delivery_id}` | Fetches detailed information for a delivery. | N/A | ```json { "message": "Fetched delivery details by id successfully!!", "statusCode": 200, "data": { "deliveryId": "uuid", "status": "DELIVERED" } } ``` |
| **Submit Delivery Review** | POST | `/api/delivery/review` | Submits a review for a completed delivery. | ```json { "deliveryId": "uuid", "rating": 5, "comments": "Very fast delivery!" } ``` | ```json { "message": "Review submitted successfully!!", "statusCode": 200 } ``` |

---

## 📂 Project Structure
```
delivery-and-cart-service
├── src
│   ├── main
│   │   ├── java/com.fooddelivery.deliverycart
│   │   │   ├── adapter/controller
│   │   │   ├── dto
│   │   │   ├── entity
│   │   │   ├── repository
│   │   │   ├── service
│   │   │   └── serviceImpl
│   │   └── resources
│   │       ├── application.yml
│   │       └── data.sql
├── Dockerfile
├── pom.xml
└── README.md
```

---

**Made with ❤️ for scalable and fast food delivery services.**
