# V&F Basket

A full-stack e-commerce web application. Customers can browse products by category, manage a cart, save delivery addresses, place orders and pay; admins can manage the catalogue. The backend is a stateless, JWT-secured REST API built with Spring Boot, and the frontend is an Angular single-page app.

## Features

**Authentication and users**
- Registration, login and email-exists check
- Stateless JWT authentication, with a token interceptor on the Angular side
- Roles stored on the user and mapped to Spring Security authorities
- Profile view and account deletion

**Catalogue**
- Category CRUD
- Product CRUD with multipart image upload (images stored in the database)
- Look up products by id, name, category id or category name
- Public browsing: product listing, category filter and product images need no login

**Cart and orders**
- Add, update, remove and clear cart items
- Place an order from the cart with a stock check and stock reservation
- Order history per user, order lookup by id, and order cancellation
- Order lifecycle: `PENDING`, `PLACED`, `CANCELLED`, `RETURNED`, `REFUNDED`, `DELIVERED`

**Payments**
- Initiate a payment for an order
- Success and failure handlers, plus a webhook endpoint
- Payment status: `PENDING`, `SUCCESS`, `FAILED`

**Reviews and addresses**
- Add, edit and delete product reviews (one review per user per product)
- Address book with a default address, full CRUD

## Tech Stack

| Layer    | Technology |
|----------|------------|
| Backend  | Java 17, Spring Boot 4.0, Spring Web MVC, Spring Data JPA (Hibernate), Spring Security, Bean Validation, Lombok |
| Auth     | JWT (jjwt 0.12.6) |
| Database | MySQL |
| Frontend | Angular 21, Angular Material, TypeScript 5.9, RxJS |
| Build    | Maven (wrapper included), Angular CLI |

## Project Structure

```
V-F-Basket/
├── V&F Basket Backend/            # Spring Boot REST API
│   └── src/main/java/com/V/FBasket/VnFBasket/
│       ├── controller/            # REST controllers (all under /vnfbasket)
│       ├── service/               # Service interfaces
│       ├── serviceImpl/           # Business logic
│       ├── dao/                   # Spring Data JPA repositories
│       ├── model/                 # JPA entities
│       ├── dto/                   # Request/response DTOs
│       ├── config/                # Security and CORS configuration
│       ├── util/                  # JWT utility and request filter
│       ├── constants/             # OrderStatus, PaymentStatus
│       └── exception/             # Global exception handling
└── V&F Basket Frontend/VnFBasketUI/   # Angular app
    └── src/app/
        ├── components/            # home, login, registration, product-card,
        │                          # add-product, navbar, my-account, user-account, address
        ├── services/              # auth, product, category, user-profile, token-interceptor
        └── models/                # user, product, category, address
```

## Data Model

`User` 1—1 `Cart` 1—N `CartItem` N—1 `Products` N—1 `Categories`
`User` 1—N `Address`, `Orders`, `Reviews`
`Orders` 1—N `OrderItems`, `Orders` 1—1 `Payments`
`Products` 1—1 `ProductImage`, 1—N `Reviews`

## API Overview

Base path: `/vnfbasket`. Everything except the public endpoints below requires a `Bearer` token.

| Area       | Endpoints |
|------------|-----------|
| Auth/User  | `POST /register`, `POST /user/login`, `GET /checkUserExists`, `GET /getUser`, `GET /getAllUsers`, `DELETE /deleteMyAccount` |
| Products   | `GET /getAllProducts`, `GET /getProductsById/{id}`, `GET /getProductsByCategoryId/{id}`, `GET /getProductsByCategoryName`, `GET /getProductByProductName/{name}`, `POST /addProduct`, `PUT /updateProduct`, `DELETE /deleteProduct/{productId}`, `GET /getProductsImageByProductId/{productId}` |
| Categories | `POST /addCategory`, `GET /getAllCategories`, `GET /getCategoryById/{categoryId}`, `GET /getCategoriesByName/{categoryName}`, `PUT /updateCategory/{categoryId}`, `DELETE /deleteCategory/{categoryId}` |
| Cart       | `POST /addToCart`, `PUT /updateCartItem`, `DELETE /removeFromCart`, `DELETE /clearCartItems`, `GET /getCartItems` |
| Orders     | `POST /placeOrder`, `GET /getOrderById/{orderId}`, `GET /getOrdersByUser`, `PUT /cancelOrder/{orderId}` |
| Payments   | `POST /initiatePayment`, `POST /handlePaymentSuccess/{orderId}`, `POST /handlePaymentFailure/{orderId}`, `POST /webhook` |
| Reviews    | `POST /addReview`, `GET /getReviewsByProductId/{productId}`, `GET /getReviewsByUserId`, `PUT /updateReview/{reviewId}`, `DELETE /deleteReview/{reviewId}` |
| Addresses  | `POST /addAddress`, `GET /getAllAddressesByUserID`, `GET /getAddressByAddressId/{addressId}`, `PUT /updateAddress`, `DELETE /deleteAddress/{addressId}` |

**Public (no token):** `/register`, `/checkUserExists`, `/user/login`, `/getAllProducts`, `/getProductsByCategoryName`, `/getProductsImageByProductId/**`

## Getting Started

### Prerequisites
- JDK 17
- MySQL 8+
- Node.js 20.19+ (required by Angular 21) and npm

### 1. Database
Create an empty MySQL database named `vf_basket`. Tables are created automatically on first run (`spring.jpa.hibernate.ddl-auto=update`).

### 2. Backend
```bash
cd "V&F Basket Backend"
# set your MySQL username/password in src/main/resources/application.properties
./mvnw spring-boot:run
```
The API starts on `http://localhost:8080`.

### 3. Frontend
```bash
cd "V&F Basket Frontend/VnFBasketUI"
npm install
npm start        # ng serve
```
Open `http://localhost:4200`.

## Current Status and Roadmap

- Backend APIs for cart, orders, payments and reviews are implemented.
- Frontend currently covers browsing, category filter, login/registration, product add/edit and the account section (profile and addresses).
- Planned:
  - Cart, checkout, order history and review screens in the UI
  - Real payment gateway integration with webhook signature verification
  - Role-based route guards and method-level authorization
  - Externalised configuration (environment variables) for DB credentials and the JWT secret
  - Unit and integration tests

## Author

Deepak Gupta — [@deepak0103gupta](https://github.com/deepak0103gupta)
