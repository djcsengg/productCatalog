# Product Catalog

This repository contains a Spring Boot application designed to manage a product catalog. It provides a RESTful API for performing CRUD (Create, Read, Update, Delete) operations on products, along with advanced search capabilities. The application is built with a service-oriented architecture, featuring persistence with JPA, caching with Redis, and integration with external APIs.

## Features

*   **REST API:** Comprehensive endpoints for managing products and categories.
*   **JPA Persistence:** Uses Spring Data JPA with a MySQL database for data storage.
*   **Service Layer Abstraction:** Decouples business logic from the controller layer with service interfaces.
*   **Dual Service Implementations:**
    *   `storageProductService`: Interacts with the local MySQL database and uses Redis for caching.
    *   `FakeStoreProductService`: An alternative implementation that integrates with the public `fakestoreapi.com`.
*   **Redis Caching:** Implemented to cache product data and reduce database load, improving response times for frequent requests.
*   **Advanced Search:** A dedicated search endpoint that supports pagination and dynamic multi-field sorting.
*   **Global Exception Handling:** Centralized exception handling using `@RestControllerAdvice` for consistent error responses.
*   **Testing:** Includes both unit tests with Mockito and MVC integration tests to ensure code quality and reliability.

## Technology Stack

*   **Backend:** Java 17, Spring Boot
*   **Data:** Spring Data JPA, Spring Data Redis
*   **Database:** MySQL
*   **Cache:** Redis
*   **Build Tool:** Apache Maven
*   **Testing:** JUnit 5, Mockito, Spring Boot Test

## Project Structure

The project is organized into the following key packages:

-   `controllers`: Contains REST controllers that handle HTTP requests (`ProductController`, `SearchController`).
-   `services`: Holds the business logic. It includes an interface (`IProductService`) and two implementations (`storageProductService` and `FakeStoreProductService`).
-   `repos`: Defines Spring Data JPA repositories for database interaction (`ProductRepo`, `CategoryRepo`).
-   `models`: Contains the JPA entity classes (`Product`, `Category`).
-   `dtos`: Data Transfer Objects for clean API communication (`ProductDto`, `SearchRequestDto`).
-   `config`: Configuration classes, such as `RedisConfig`.
-   `test`: Contains all unit and integration tests.

## Prerequisites

Before running the application, ensure you have the following installed:
*   Java 17 (or later)
*   Apache Maven
*   A running MySQL instance
*   A running Redis instance

## Configuration

1.  Open the `src/main/resources/application.properties` file.
2.  Update the database connection properties to match your MySQL setup:
    ```properties
    spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/ProductCatalogService
    spring.datasource.username=root
    spring.datasource.password=admin
    ```
3.  Ensure your Redis server is running on the default host and port, or update the properties accordingly:
    ```properties
    spring.data.redis.host=localhost
    spring.redis.port=6379
    ```

## Running the Application

You can run the application using the Maven wrapper:

```bash
./mvnw spring-boot:run
```
The application will start on `http://localhost:8080`.

## API Endpoints

### Product Management

The following endpoints are available for managing products under the `/products` path. The default service implementation (`storageProductService`) is used.

| Method | Endpoint         | Description                   |
|--------|------------------|-------------------------------|
| `GET`  | `/products`      | Retrieve a list of all products. |
| `GET`  | `/products/{id}` | Retrieve a single product by its ID. |
| `POST` | `/products`      | Create a new product.         |
| `PUT`  | `/products/{id}` | Update an existing product.   |
| `DELETE`| `/products/{id}`| Delete a product by its ID.   |

**Example `POST /products` Body:**
```json
{
    "name": "Laptop Pro",
    "description": "A high-performance laptop.",
    "imageUrl": "http://example.com/image.png",
    "price": 1499.99,
    "category": {
        "name": "Electronics"
    }
}
```

### Search

The application provides an advanced search endpoint with pagination and sorting.

| Method | Endpoint   | Description                               |
|--------|------------|-------------------------------------------|
| `POST` | `/search`  | Search for products based on a query. |

**Example `POST /search` Body:**
This request searches for products named "Laptop", on the first page (`pageNumber: 0`) with 5 items per page, sorted by price in descending order.
```json
{
    "query": "Laptop",
    "pageNumber": 0,
    "pageSize": 5,
    "sortParams": [
        {
            "paramName": "productPrice",
            "sortType": "DESC"
        }
    ]
}
```

## Testing

To run the full suite of unit and integration tests, use the following Maven command:

```bash
./mvnw test
