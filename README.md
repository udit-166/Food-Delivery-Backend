# 🍽️ Restaurant Service - Food Delivery App

This microservice is responsible for managing **restaurants**, **categories**, and **food items** in a scalable food delivery system.

---

## 📁 Project Structure

```
restaurant-service
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.fooddelivery.restaurantservice
│   │   │       ├── core
│   │   │       │   ├── constant               # Constants used across the service
│   │   │       │   ├── model                  # Internal models or DTOs
│   │   │       │   ├── repository             # Interfaces for data access
│   │   │       │   ├── service                # Service interfaces
│   │   │       │   └── serviceImpl            # Implementations of service logic
│   │   │       ├── adapter
│   │   │       │   ├── controller             # REST API controllers
│   │   │       │   ├── entity                 # JPA entities (e.g., Restaurant, FoodItem)
│   │   │       │   ├── repository             # Spring Data JPA Repositories
│   │   │       │   └── usecase                # Use case logic coordinating services
│   │   └── resources
│   │       ├── application.yml               # Main configuration file
│   │       └── data.sql                      # Sample or seed data (optional)
├── Dockerfile
├── README.md
└── pom.xml
```

---

## ⚙️ Features

- 🏪 **Restaurant Management**: CRUD operations for restaurants (add, update, retrieve, delete).
- 🍔 **Food Item Management**: CRUD for food items, linked with categories and restaurants.
- 📂 **Category Management**: Create and update categories for food items.
- ⭐ **Ratings System**: Submit and fetch food ratings.
- 🔎 **Smart Search API**:
  - Search by **restaurant name**, **food name**, or **category**.
  - Restaurant match → returns restaurant + menu.
  - Food/Category match → returns list of matching food items.
- ⚡ **Redis Caching**: Menu (grouped by category) is cached for 15 minutes after first access.
- 🧠 **Fallback Search**: If no match in restaurant, fallback to category → food search.

---

## 📌 API Endpoints

### **Category APIs**
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/categories` | Create a new category |
| `PUT` | `/categories/{id}` | Update category details |

---

### **Food Item APIs**
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/food` | Add a new food item |
| `GET` | `/food` | Get all food items |
| `GET` | `/food/restaurant/{restaurantId}` | Get all food items of a restaurant |
| `GET` | `/food/category/{categoryId}` | Get all food items of a category |
| `GET` | `/food/{id}` | Get a specific food item by ID |
| `PUT` | `/food/{id}` | Update a food item |
| `DELETE` | `/food/{id}` | Delete a food item |
| `POST` | `/food/{id}/rating` | Submit food rating |

---

### **Restaurant APIs**
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/restaurants` | Create a new restaurant |
| `GET` | `/restaurants` | Get all restaurants |
| `PUT` | `/restaurants/{id}` | Update restaurant details |
| `DELETE` | `/restaurants/{id}` | Delete a restaurant |
| `GET` | `/restaurants/{id}/menu` | Get menu of a restaurant |
| `PUT` | `/restaurants/{id}/status` | Open or close a restaurant |
| `GET` | `/restaurants/search?query=` | Search for restaurants/food |

---

## 🔗 External Integrations

- **Auth-Service** → Validates restaurant owner role (`RESTAURANT_OWNER`)
- **Order-Service** → Uses menu and pricing for order validation
- **Cart & Delivery Service** → Uses restaurant location for delivery assignment

---

## 🧪 Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Cloud OpenFeign**
- **PostgreSQL** (main DB)
- **Redis** (caching)
- **Docker**

---

## 📌 Notes
- Menu responses are cached for **15 minutes** to optimize performance.
- Smart search provides **fallback** from restaurant → category → food.
---
## ⚙️ Run Locally

**Pre-reqs:** Java 17+, Redis, PostgreSQL, Kafka 
**Steps:**
```bash
git clone https://github.com/your-username/Food-Delivery-Backend.git
cd Food-Delivery-Backend && git checkout auth-service
# Update application.yml for DB & Redis configs
./mvnw spring-boot:run

