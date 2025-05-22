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
3. [Assumptions / Current limitations](#3-assumptions--current-limitations)
4. [Example Usage](#4-example-of-using-the-application)


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

#### 5. `store_date_batch`
Stores product category labels:
- `id` (PK)
- `store_id` (references `store`)
- `batch_date` (e.g. `2025-05-01`)
- UNIQUE(store_id, batch_date)
---

#### 6. `store_date_batch_product`
Stores product category labels:
- `id` (PK)
- `store_product_batch_id ` (references `store_date_batch`)
- `product_id` (references `product`)
- `price`
- `currency`
---

#### 7. `store_discount_date_batch`
Stores product category labels:
- `id` (PK)
- `store_id ` (references `store`)
- `from_date` (e.g. `2025-05-01`)
- `to_date` (e.g. `2025-05-08`)
- UNIQUE(store_id, from_date, to_date)
---

#### 8. `store_discount_date_batch_product`
Stores product category labels:
- `id` (PK)
- `store_discount_date_batch_id ` (references `store_discount_date_batch`)
- `product_id` (references `product`)
- `percentage_of_discount`

---
### Schema Diagram

![Database Schema](./docs/DATABASE_SCHEMA.png)

### 1.3 Structure of Folders



The backend code is organized into clear, modular packages to follow the separation of concerns principle and to make future extensions easier to implement. Below is an example for each folder:
```text
src/
├── controller/
│   └── BasketOptimizerController.java      # Handles incoming HTTP requests for the optimization of the basket
├── service/
│   ├── database/
│   │   └── ProductService.java             # Business logic related to database entities
│   └── feature/
│       └── BasketQueryService.java         # Logic related to DTO processing and conversion for a feature
├── dao/
│   ├── database/
│   │   └── ProductRepository.java          # Standard repository for database access
│   └── features/
│       └── BasketOptimizerRepository.java  # Custom queries and logic for basket optimization feature
├── dto/
│   └── ProductPriceDTO.java                # Data Transfer Object used as the smallest building block in a response
└── entity/
    └── Product.java                        # Entity class mapped to the database
```


The `test` folder mirrors the structure of the `src` folder to ensure clear separation between implementation and testing. Each component has corresponding tests in the `test` folder, organized as follows:
```declarative
src/
└── test/
    ├── controller/
    │   └── BasketOptimizerControllerTest.java      # Teste pentru endpointul BasketOptimizerController (teste de integrare)
    ├── service/
    │   ├── database/
    │   │   └── ProductServiceTest.java             # Teste unitare pentru ProductService
    │   └── feature/
    │       └── BasketQueryServiceTest.java         # Teste unitare pentru BasketQueryService
    ├── dao/
    │   ├── database/
    │   │   └── ProductRepositoryTest.java          # Teste unitare pentru ProductRepository
    │   └── features/
    │       └── BasketOptimizerRepositoryTest.java  # Teste unitare pentru BasketOptimizerRepository
    ├── dto/
    │   └── ProductPriceDTOTest.java                # Teste unitare pentru ProductPriceDTO (dacă e cazul)
    └── entity/
        └── ProductTest.java                        # Teste unitare pentru entitatea Product (dacă e necesar)
```
## 2. Build and Run Application
## 2.1 Requirements
- **Java 17** installed and configured in your system.
- **MySQL Server** running on port `3306`.
- A MySQL user with the following credentials:
    - **Username**: `springstudent`
    - **Password**: `springstudent`
- A database named **PRODUCT_DATABASE** must exist before running the application.  
  This is where all tables and data will be created and stored.


## 3. Assumptions / Current limitations:
- THE CSV FILES for discounts and prices are not duplicated: they had correct naming and there is no duplicated data.
- For simplicity, the service assumes that the products sent in the request are written exactly as they appear in the database.
  For example, "lapte zuzu" must match the product name exactly.
  In a real application, users will select products from predefined lists, so this limitation does not affect the final user experience.


**Mention**: These assumptions were made because I focused on implementing the core functionalities and did not test every edge case due to the limited 2-week development time.



## 4. Example of Using the Application

To use this application, follow the steps below:

### 4.1. Download the Application
Clone the repository to your local machine using Git:
```
git clone git@github.com:MarianVJ/priceComparatorBackend.git
```

### 4.2 Build the Application
To build the application, use the following Maven wrapper command:
```
./mvnw clean package
```



### 4.3 Run the application
This will start the Spring Boot application. By default, it should be accessible at `http://localhost:8080`.
```
./mvnw spring-boot:run
```
### 4.4 Features

Each feature has its own classes for Controller, Service, and Repository. This decision was made considering the possibility of extending the features with more complex or additional functionality.
### 4.4.1 Daily Shopping Basket Monitoring

Help users split their basket into shopping lists that optimise for cost savings.


#### Usage


**Request Example:**  
`POST http://localhost:8080/rest/basket-optimizer/optimize`

Request body:
```json
[
  {
    "date": "2025-05-13",
    "products": ["lapte zuzu", "cafea măcinată", "iaurt grecesc", "apa de izvor"]
  }
]
```

*Response Example:*
```json
[
  {
    "recommendedShopping": {
      "lidl": [
        {
          "name": "cafea măcinată",
          "price": 19.21
        },
        {
          "name": "lapte zuzu",
          "price": 8.62
        }
      ],
      "profi": [
        {
          "name": "iaurt grecesc",
          "price": 10.37
        }
      ]
    },
    "storeTotals": {
      "lidl": 27.83,
      "profi": 10.37
    },
    "basketTotal": 38.199999999999996,
    "notes": [
      "Product not found: apa de izvor"
    ]
  }
]
```

#### Implementation Details

The following components are used in the backend implementation, each located in their respective package:

- `rest/ProductController` – processes the requests for the `rest/basket-optimizer/optimize` endpoint.
- `service/ProductService` – applies the business logic (how the `BasketOptimizationResponse` is built).
- `dao/ProductRepository` – accesses the database and performs the query using native SQL syntax for operation optimization (uses a query with `ROW_NUMBER()`).
- `dto/ProductDTO` – used for sending data in the response (smallest building block).