# Auth Service - Food Delivery App 🍔🔐

The **Auth Service** is a Spring Boot microservice responsible for handling user authentication, OTP-based login, JWT token management, Google login, and user profile operations in the food delivery backend system.

---

## ✅ Features

- 📱 Login via phone number (OTP-based)
- 🔐 JWT authentication & refresh tokens
- 🚀 Google login support
- 📦 Register & manage user profiles
- 🧠 Update user metadata (device info, login time)
- 🗺️ Manage user addresses
- ❌ Deactivate user accounts
- ⚡ Redis-backed OTP generation & validation
- 🔒 Role-based access (Admin/User)

---

## 🛠️ Tech Stack

- Java 17, Spring Boot 3.x
- Spring Security, JWT
- Redis (OTP storage)
- PostgreSQL (user data)
- Maven, Lombok

---

## 📁 Basic Package Structure

- `com.auth.user.core.controller` - Contains the `AuthController` for handling REST API requests.
- `com.auth.user.core.usecase` - Contains the business logic implementation (`UserUsecaseImpl`).
- `com.auth.user.adapter.service` - Service layer with core authentication logic.
- `com.auth.user.adapter.repository` - Interfaces for database access.
- `com.auth.user.core.entity` - Entity classes for User and Address.
- `com.auth.user.core.model` - DTOs and models used in the system.
- `com.auth.user.core.utils` - Utility classes (JWT, token validator, etc.)

---

## 📌 Auth Service APIs

| Endpoint                       | Method | Description                                      |
|--------------------------------|--------|--------------------------------------------------|
| `/auth/login`                  | POST   | Login via phone number or Google                 |
| `/auth/register-user`          | POST   | Register a new user                              |
| `/auth/refresh-token`          | POST   | Generate a new JWT token                         |
| `/auth/send-otp/{phone_number}`| GET    | Send OTP to phone number                         |
| `/auth/verify-otp`             | POST   | Verify the received OTP                          |

---

## 👤 User Service APIs

| Endpoint                                | Method | Description                                         |
|-----------------------------------------|--------|-----------------------------------------------------|
| `/user/get-profile/phone/{phone_number}`| GET    | Get user profile by phone number                    |
| `/user/get-profile/email/{email}`       | GET    | Get user profile by email                           |
| `/user/update-profile`                  | PUT    | Update user profile                                 |
| `/user/role`                            | GET    | Get user's role                                     |
| `/user/address/current`                 | POST   | Get current address based on location               |
| `/user/address/all`                     | GET    | Get all addresses of the user                       |
| `/user/address/save`                    | POST   | Save a new address                                  |
| `/user/address/update`                  | PUT    | Update an existing address                          |
| `/user/metadata/update`                 | PUT    | Update app metadata (version, device ID, FCM token) |
| `/user/deactivate`                      | DELETE | Deactivate a user account                           |

> **Note:** All secured endpoints require JWT in `Authorization` header.

---

## ⚙️ Run Locally

**Pre-reqs:** Java 17+, Redis, PostgreSQL  
**Steps:**
```bash
git clone https://github.com/your-username/Food-Delivery-Backend.git
cd Food-Delivery-Backend && git checkout auth-service
# Update application.yml for DB & Redis configs
./mvnw spring-boot:run
