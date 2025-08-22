
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
- **eSewa payment gateway integration for online payments**

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

Create a database named `agritech` (or name of your choice).

```sql
CREATE DATABASE agritech;
```

3. Configure `application.properties` or `application.yml`

Update database connection details, JWT secrets, and eSewa configs in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/agritech
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password

spring.jpa.hibernate.ddl-auto=update

jwt.secret=YourJWTSecretKey
jwt.expiration=3600000

# eSewa Payment Gateway Configuration
esewa.merchantId=YOUR_MERCHANT_ID
esewa.secretKey=YOUR_SECRET_KEY
esewa.successUrl=https://your-domain.com/api/payment/success
esewa.failUrl=https://your-domain.com/api/payment/fail
```

### Build and Run

Build the project using Maven:

```bash
mvn clean install
```

Run the application:

```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

## API Endpoints

- **Authentication**
  - `POST /api/users/register` — Register new users
  - `POST /api/users/login` — Login and get JWT token

- **Products**
  - `GET /api/products` — List all products
  - `GET /api/products/{id}` — Get product by ID
  - `POST /api/products` — Create new product (authentication required)
  - `PUT /api/products/{id}` — Update product (authentication required)
  - `DELETE /api/products/{id}` — Delete product (authentication required)
  - `POST /api/products/uploadProductImage` — Upload product image

- **Reviews**
  - `GET /api/reviews?productId={productId}` — Get reviews for a product
  - `POST /api/reviews` — Add a review (authentication required)

- **Payments**
  - `POST /api/payment/initiate` — Initiate eSewa payment request
  - `POST /api/payment/verify` — Verify payment status from eSewa callback

## Security

- User passwords are hashed securely using BCrypt.
- JWT tokens secure and authorize API access.
- Role-based access control with Spring Security protects sensitive endpoints.

## eSewa Payment Gateway Integration

The backend integrates with the eSewa payment gateway to facilitate online payments for orders or services.

- **Initiate payment:** Backend endpoint accepts payment details and creates a payment request to eSewa.
- **Verify payment:** Backend verifies payment notifications (callbacks) from eSewa using their API.
- **Update order status:** Orders are updated based on payment success or failure.

### Configuration

Add your merchant credentials and URLs to application properties as shown above.

### Important Notes

- Always verify payment transactions on the backend securely to prevent fraud.
- Test integration thoroughly using eSewa sandbox before going live.
- Log all payment transactions carefully.

## Environment Variables

| Variable                    | Description                    | Default / Example                       |
|-----------------------------|------------------------------|---------------------------------------|
| `JWT_SECRET`                | JWT signing secret key        | `YourJWTSecretKey`                    |
| `SPRING_DATASOURCE_URL`     | Database connection string    | `jdbc:postgresql://localhost:5432/agritech` |
| `SPRING_DATASOURCE_USERNAME`| Database username             | `your_db_username`                    |
| `SPRING_DATASOURCE_PASSWORD`| Database password             | `your_db_password`                    |
| `ESEWA_MERCHANTID`          | eSewa merchant ID             | `YOUR_MERCHANT_ID`                    |
| `ESEWA_SECRETKEY`           | eSewa secret key              | `YOUR_SECRET_KEY`                     |
| `ESEWA_SUCCESSURL`          | eSewa payment success callback URL | `https://your-domain.com/api/payment/success` |
| `ESEWA_FAILURL`             | eSewa payment failure callback URL | `https://your-domain.com/api/payment/fail` |

## Contributing

Contributions are welcome! Please fork the repository and submit pull requests for improvements or bug fixes.

## License

This project is licensed under the MIT License.

***

Feel free to customize repository URLs, endpoints, and configuration values to fit your project specifics. If you want, I can help write example code or API documentation for the eSewa integration.
