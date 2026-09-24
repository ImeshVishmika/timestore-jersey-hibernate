# TimeStore – Jersey + Hibernate Edition

![Java](https://img.shields.io/badge/Java-Backend-orange)
![Jersey](https://img.shields.io/badge/Jersey-JAX--RS-blue)
![Hibernate](https://img.shields.io/badge/Hibernate-ORM-green)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue)
![Maven](https://img.shields.io/badge/Maven-Build-red)
![Tomcat](https://img.shields.io/badge/Tomcat-Server-yellow)

A full-stack **luxury timepiece e-commerce platform** built with **Java, Jersey (JAX-RS), Hibernate, and MySQL**.

The application allows users to browse luxury watches, purchase them online using the **PayHere Sandbox payment gateway**, and manage their purchase history. Administrators can manage users, products, models, and analyze revenue.

---

# Project Overview

TimeStore is a **Java-based web application** following a layered architecture.

The platform integrates:

* **Jersey (JAX-RS)** for RESTful API endpoints
* **Hibernate ORM** for database persistence
* **MySQL** as the relational database
* **HTML + JavaScript** frontend
* **Apache Tomcat** as the application server
* **Maven** for dependency management and builds

Future deployment plans include **Docker containerization**.

---

# System Architecture

```mermaid
flowchart LR

User[User Browser]
Frontend[HTML + JavaScript UI]
API[Jersey REST API]
Service[Service Layer]
DAO[DAO Layer]
Hibernate[Hibernate ORM]
DB[(MySQL Database)]
Payment[PayHere Sandbox]

User --> Frontend
Frontend --> API
API --> Service
Service --> DAO
DAO --> Hibernate
Hibernate --> DB
Service --> Payment
```

---

# Application Features

## User Features

* Browse luxury watch collections
* View detailed product information
* PayHere Sandbox payment integration for checkout testing
* View purchase history
* Manage personal account

## Admin Features

* Manage users
* Add, update, and delete products
* Manage watch models
* View total revenue
* View revenue per product
* Monitor platform activity

---

# Technology Stack

| Layer              | Technology        |
| ------------------ | ----------------- |
| Backend            | Java              |
| REST Framework     | Jersey (JAX-RS)   |
| ORM                | Hibernate         |
| Database           | MySQL             |
| Build Tool         | Maven             |
| Application Server | Apache Tomcat     |
| Frontend           | HTML + JavaScript |
| Payment Gateway    | PayHere Sandbox   |

---

# Project Structure

```mermaid
flowchart TD

src[src/main/java]

config[config]
controllers[controllers]
services[services]
dao[dao]
models[models]

src --> config
src --> controllers
src --> services
src --> dao
src --> models
```

Typical structure:

```
src/main/java
 ├── config
 ├── controllers
 ├── services
 ├── dao
 ├── models
```

---

# Admin Workflow

Administrators manage system operations through an admin dashboard.

```mermaid
flowchart LR

Admin[Admin]
AdminUI[Admin Dashboard]
AdminAPI[Jersey REST API]
AdminService[Admin Service Layer]
AdminDAO[DAO Layer]
DB[(MySQL Database)]
Reports[Revenue Reports]

Admin --> AdminUI
AdminUI --> AdminAPI
AdminAPI --> AdminService
AdminService --> AdminDAO
AdminDAO --> DB
AdminService --> Reports

AdminUI -->|Manage Users| AdminAPI
AdminUI -->|Manage Products| AdminAPI
AdminUI -->|Manage Models| AdminAPI
AdminUI -->|View Revenue| AdminAPI
```

---

# Payment Processing Flow

TimeStore integrates **PayHere Sandbox** to simulate secure online payments during development.

```mermaid
sequenceDiagram

participant User
participant UI as HTML/JS Frontend
participant API as Jersey REST API
participant Service as Order Service
participant PayHere as PayHere Sandbox
participant DB as MySQL Database

User->>UI: Select product and checkout
UI->>API: Submit order request
API->>Service: Validate order
Service->>DB: Verify product and user
DB-->>Service: Data response
Service-->>API: Payment ready
API-->>UI: Payment initialization

UI->>PayHere: Redirect to payment gateway
PayHere-->>UI: Payment success response
UI->>API: Confirm payment
API->>Service: Save order
Service->>DB: Store order and purchase history
DB-->>Service: Success
Service-->>API: Order completed
API-->>UI: Show confirmation
UI-->>User: Purchase complete
```

---

# Database Entity Relationship

```mermaid
erDiagram

USER {
 int id
 string name
 string email
 string password
}

PRODUCT {
 int id
 string name
 double price
 int model_id
}

MODEL {
 int id
 string name
 string brand
}

ORDER {
 int id
 int user_id
 datetime order_date
}

ORDER_ITEM {
 int id
 int order_id
 int product_id
 int quantity
}

USER ||--o{ ORDER : places
ORDER ||--o{ ORDER_ITEM : contains
PRODUCT ||--o{ ORDER_ITEM : purchased
MODEL ||--o{ PRODUCT : categorizes
```

---

# API Documentation

| Method | Endpoint        | Description           |
| ------ | --------------- | --------------------- |
| GET    | /product/load   | Load all products     |
| POST   | /product/add    | Add new product       |
| PUT    | /product/update | Update product        |
| DELETE | /product/delete | Delete product        |
| GET    | /model/load     | Load models           |
| POST   | /model/add      | Add model             |
| POST   | /user/login     | Authenticate user     |
| GET    | /user/history   | View purchase history |

---

# Application Screenshots

## User Pages

### Home Page
<img src="docs/images/user/Home.png" alt="TimeStore home page" width="800">

### Product Page
<img src="docs/images/user/ProductPage.png" alt="TimeStore product page" width="800">

### Search Page
<img src="docs/images/user/searchPage.png" alt="TimeStore product search page" width="800">

### Product Purchase Window
<img src="docs/images/user/ProductBuyWindow.png" alt="TimeStore product purchase window" width="800">

### Checkout Page
<img src="docs/images/user/checkoutPage.png" alt="TimeStore checkout page" width="800">

### PayHere Payment Window
<img src="docs/images/user/checkoutPayhereWindow.png" alt="PayHere payment window" width="800">

### PayHere Details
<img src="docs/images/user/Payheredetails.png" alt="PayHere payment details" width="800">

### Payment Success
<img src="docs/images/user/PaymentSuccessWindow.png" alt="Payment success confirmation" width="800">

## Admin Pages

### Admin Dashboard
<img src="docs/images/admin/AdminDashboard.png" alt="TimeStore admin dashboard" width="800">

### Customers
<img src="docs/images/admin/customers.png" alt="TimeStore customer management page" width="800">

### Orders
<img src="docs/images/admin/orders.png" alt="TimeStore order management page" width="800">

### Products
<img src="docs/images/admin/product.png" alt="TimeStore product management page" width="800">

### Messages
<img src="docs/images/admin/messages.png" alt="TimeStore admin messages page" width="800">

### Settings
<img src="docs/images/admin/settings.png" alt="TimeStore admin settings page" width="800">

---

# Running the Project

## Requirements

* Java 17+
* Maven
* MySQL
* Apache Tomcat

---

## Clone Repository

```
git clone https://github.com/ImeshVishmika/timestore-jersey-hibernate
```

---

## Configure Database

Create database:

```
CREATE DATABASE timestore;
```

Update database credentials in:

```
hibernate.cfg.xml
```

---

## Build Project

```
mvn clean install
```

## Naming Convention

All variables in Java code should follow lowerCamelCase naming.

Examples:

* productName
* deliveryMethodId
* createdAt

Automated validation runs during Maven `validate` via Checkstyle.

To run only naming validation:

```
mvn checkstyle:check
```

JavaScript variable naming is enforced via ESLint camelCase rules.

To run JavaScript naming validation:

```
npm run lint:naming
```

---

## Deploy to Tomcat

Copy the generated WAR file to:

```
tomcat/webapps/
```

Start Tomcat and open the application in your browser.


# Related Implementations

This project has another implementation using PHP.

* PHP Version → https://github.com/ImeshVishmika/timestore-php
* Java Jersey + Hibernate Version → https://github.com/ImeshVishmika/timestore-jersey-hibernate

---

# Author

Developed as a learning project exploring:

* REST API design with Jersey
* ORM persistence with Hibernate
* Java web application architecture
* Payment gateway integration
* E-commerce platform development
