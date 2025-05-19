# Price Comparator - Spring Boot Backend Application



## Description

A backend service that helps users compare prices of everyday grocery items
across different supermarket chains (e.g., Lidl, Kaufland, Profi). The system should allow
users to track price changes, find the best deals, and manage their shopping lists
effectively.


## Table of Contents

1. [Backend Design](#1-backend-design)
    - [Application Architecture](#1.1-application-architecture)
    - [Database](#1.2-database)
2. [Build and Run Application](#2-build-and-run-application)
3. [Example Usage](#3-example-of-using-the-application)



## 1. Backend Design

### 1.2. Application Architecture
![alt text](./docs/APPLICATION_ARCHITECTURE.jpg)
This diagram shows the main layers of the backend application:
- REST Controller exposes API endpoints
- Service layer contains business logic
- DAO handles DB operations using JPA
- MySQL stores persistent product data


[//]: # (--------------)
### 1.2 Database

The backend uses a MySQL database with the following tables:

#### 1. `product`
Stores product information:
- `product_id` (PK)
- `name`
- `package_quantity`
- `package_unit`
- `brand_id` (references `Brand`)
- `category_id` (references `Category`)


---


---

#### 2. `brand`
Contains a fixed list of shops:
- `id` (PK)
- `name` (e.g., Lidl, Pilos, Zuzu etc.)



---

#### 3. `category`
Stores product category labels:
- `id` (PK)
- `name` (e.g., Lactate, Panificatie, Carne, etc.)

---

#### 4. `store`
Stores product category labels:
- `id` (PK)
- `name` (e.g., Lidl, Kaufland, Profi  etc.)
  **Initial entries:** 3 shops
---

#### 5. `store_product_batch`
Stores product category labels:
- `id` (PK)
- `store_id` (references `store`)
- `batch_date` (e.g. `2025-05-01`)
- UNIQUE(store_id, batch_date)
---

#### 6. `store_product`
Stores product category labels:
- `id` (PK)
- `store_product_batch_id ` (references `store_product_batch`)
- `product_id` (references `product`)
- `price`
- `currency`
---

### Schema Diagram

![Database Schema](./docs/DATABASE_SCHEMA.png)


## 2. Build and Run Application
## 2.1 Requirements
- **Java 17** installed and configured in your system.
- **MySQL Server** running on port `3306`.
- A MySQL user with the following credentials:
    - **Username**: `springstudent`
    - **Password**: `springstudent`
- A database named **PRODUCT_DATABASE** must exist before running the application.  
  This is where all tables and data will be created and stored.

## 3. Example of Using the Application

To use this application, follow the steps below:

### 3.1. Download the Application
Clone the repository to your local machine using Git:
```
git clone git@github.com:MarianVJ/priceComparatorBackend.git
```

### 3.2 Build the Application
To build the application, use the following Maven wrapper command:
```
./mvnw clean package
```



### 3.3 Run the application
This will start the Spring Boot application. By default, it should be accessible at `http://localhost:8080`.
```
./mvnw spring-boot:run
```
