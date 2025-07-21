# 🍽️ Restaurant Service - Food Delivery App

This microservice is responsible for managing restaurant data and food items in a scalable food delivery system.

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

- 🏪 **Restaurant Management**: Add, update, and retrieve restaurant information.
- 🍔 **Food Item Management**: Add and list food items under restaurants.
- 🔄 **Auto Create/Update Restaurant**: If a food item is added and the restaurant doesn't exist, it creates one and updates the average rating accordingly.
- 🧠 **Smart Search API**:
  - Search by **restaurant name**, **food name**, or **category**.
  - If match is a **restaurant** → returns restaurant + food items.
  - If match is a **food or category** → returns list of matching food items.
- ⚡ **Redis Caching**: Caches restaurant menu (grouped by category) for 15 minutes after first access.

---

## 🔗 External Integrations

- 🔐 **auth-service**: To validate and manage user roles such as `RESTAURANT_OWNER`.
- 🧑‍🍳 **User Linking**: Each restaurant is linked to a user (restaurant owner) via:
  - `restaurant_email`
  - `customer_care_number`
  - `opening_time`
  - `closing_time`

---

## 🧪 Tech Stack

- 🧬 Java 17
- ☕ Spring Boot
- 🧠 Spring Data JPA
- 🔁 Spring Cloud OpenFeign
- 🗃️ PostgreSQL (Main DB)
- 🚀 Redis (Caching)
- 🐳 Docker

---

## 🚀 Getting Started (Optional)

Coming soon! Let me know if you'd like a setup and run guide here.

---

## 📌 Contributing

Feel free to open issues and contribute PRs to improve this service!

---

## 📧 Contact

For any questions or suggestions, feel free to reach out!

---

**Made with ❤️ for fast and scalable food delivery systems.**
