
# Agritech Backend

This is the backend API for the Agritech platform, developed using Spring Boot. It provides RESTful services for managing agricultural products, users, authentication, and more. The backend uses PostgreSQL for data persistence and JWT (JSON Web Tokens) for secure authentication.

## Features

- User registration and login with JWT-based authentication and authorization
- CRUD operations for agricultural products, categories, and manufacturers
- Secure API endpoints with role-based access control
- Image upload support for product images
- Review management for products
- Supports pagination and filtering on relevant endpoints
- Integration with PostgreSQL database for reliable data storage

## Technology Stack

- **Backend Framework:** Spring Boot
- **Database:** PostgreSQL
- **Authentication:** JWT (JSON Web Tokens)
- **ORM:** Spring Data JPA (Hibernate)
- **Build Tool:** Maven or Gradle
- **Security:** Spring Security

## Getting Started

### Prerequisites

- Java JDK 17 or higher
- PostgreSQL database (installed and running)
- Maven or Gradle build tool installed
- (Optional) Postman or any API testing tool

### Configuration

1. Clone the repository

```bash
git clone https://github.com/yourusername/agritech-backend.git
cd agritech-backend
```

2. Set up the PostgreSQL database

Create a database named `agritech` (or any name you prefer).

```sql
CREATE DATABASE agritech;
```

3. Configure `application.properties` or `application.yml`

Update your database connection and JWT secrets in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/agritech
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password

spring.jpa.hibernate.ddl-auto=update

jwt.secret=YourJWTSecretKey
jwt.expiration=3600000
```

### Build and Run

Build the project with Maven:

```bash
mvn clean install
```

Run the application:

```bash
mvn spring-boot:run
```

The API will start and listen on `http://localhost:8080`.

## API Endpoints

- **Authentication**
  - `POST /api/users/register` — Register new user
  - `POST /api/users/login` — Login and get JWT token

- **Products**
  - `GET /api/products` — List all products
  - `GET /api/products/{id}` — Get a product by ID
  - `POST /api/products` — Create new product (auth required)
  - `PUT /api/products/{id}` — Update product (auth required)
  - `DELETE /api/products/{id}` — Delete product (auth required)
  - `POST /api/products/uploadProductImage` — Upload product image

- **Reviews**
  - `GET /api/reviews?productId={productId}` — Get reviews by product
  - `POST /api/reviews` — Add a review (auth required)

(You can add more API documentation here)

## Security

- Passwords are securely hashed using BCrypt.
- JWT tokens are signed with a secret key and validated on each request.
- API endpoints are protected with role-based access via Spring Security annotations.

## Environment Variables

You can customize configuration by setting environment variables or overriding the `application.properties`.

| Variable          | Description                | Default                  |
|-------------------|----------------------------|--------------------------|
| `JWT_SECRET`      | JWT signing secret key     | `YourJWTSecretKey`       |
| `SPRING_DATASOURCE_URL`      | Database connection string | `jdbc:postgresql://localhost:5432/agritech` |
| `SPRING_DATASOURCE_USERNAME` | Database username          | `your_db_username`       |
| `SPRING_DATASOURCE_PASSWORD` | Database password          | `your_db_password`       |

## Contributions

Contributions are welcome! Please open a pull request or submit an issue.

## License

This project is licensed under the MIT License.

***

Feel free to adjust the repository URL, properties, and specific features according to your project details. If you want, I can help generate more detailed API documentation or setup instructions.
