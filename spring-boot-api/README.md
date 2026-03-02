# Spring Boot REST API - Production Ready

A professional Spring Boot REST API with CRUD operations, featuring industry best practices including validation, error handling, pagination, and comprehensive API documentation.

## Key Features
- Bean Validation - Input validation with custom error messages
- Global Exception Handling - Consistent error responses (404, 409, 400)
- DTO Pattern - Separate Request/Response DTOs (CreateUserRequest, UpdateUserRequest, UserDTO)
- Swagger/OpenAPI - Interactive API documentation
- Pagination & Sorting - Efficient data retrieval for large datasets
- Logging - SLF4J logging for observability
- Transaction Management - ACID compliance
- Duplicate Prevention - Email uniqueness validation with custom validator
- MapStruct - Type-safe DTO mapping
- Lombok - Reduced boilerplate code
- Spring Actuator - Health checks and metrics
- Unit Tests - JUnit 5 with Mockito

## Project Structure
```
src/main/java/com/example/api/
├── Application.java          # Main application entry point
├── config/
│   └── OpenApiConfig.java    # Swagger/OpenAPI configuration
├── controller/
│   └── UserController.java   # REST endpoints
├── dto/
│   ├── ApiResponse.java      # Standardized API response wrapper
│   ├── CreateUserRequest.java # Request DTO for creating users
│   ├── UpdateUserRequest.java # Request DTO for updating users
│   └── UserDTO.java          # Response DTO
├── exception/
│   ├── DuplicateEmailException.java
│   ├── GlobalExceptionHandler.java
│   └── UserNotFoundException.java
├── mapper/
│   └── UserMapper.java       # MapStruct DTO mapper
├── model/
│   └── User.java             # JPA Entity
├── repository/
│   └── UserRepository.java   # Data access layer
├── service/
│   └── UserService.java      # Business logic
└── validation/
    ├── UniqueEmail.java      # Custom validation annotation
    └── UniqueEmailValidator.java
```

## Technologies
- Spring Boot 3.2.0
- Spring Data JPA
- Spring Validation
- Spring Actuator
- H2 In-Memory Database
- Lombok 1.18.30
- MapStruct 1.5.5
- SpringDoc OpenAPI 2.2.0
- JUnit 5 & Mockito
- Java 17

## Build & Run
The application now uses port `8081` by default (set in `src/main/resources/application.properties`) to avoid conflicts with services like Jenkins.

```bash
mvn clean install
# Run (uses `server.port` from application.properties)
mvn spring-boot:run
# Or override the port at runtime:
# mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
# java -jar target/your-app.jar --server.port=8081
```

## API Endpoints
- GET    /api/users                    - Get all users
- GET    /api/users/paginated          - Get users with pagination & sorting
- GET    /api/users/{id}               - Get user by ID
- POST   /api/users                    - Create new user (with validation)
- PUT    /api/users/{id}               - Update user (with validation)
- DELETE /api/users/{id}               - Delete user

## API Documentation
Interactive Swagger UI: http://localhost:8081/swagger-ui.html
OpenAPI JSON: http://localhost:8081/v3/api-docs

## Test with curl
```bash
# Create user (with validation)
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"John","email":"john@example.com"}'

# Get all users
curl http://localhost:8081/api/users

# Get users with pagination
curl "http://localhost:8081/api/users/paginated?page=0&size=5&sortBy=name"

# Get user by ID
curl http://localhost:8081/api/users/1

# Test validation error (invalid email)
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"A","email":"invalid"}'

# Test duplicate email (409 Conflict)
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Jane","email":"john@example.com"}'
```

## What Makes This Project Stand Out

### 1. Professional Error Handling
- Custom exceptions (UserNotFoundException, DuplicateEmailException)
- Global exception handler with consistent JSON responses
- Proper HTTP status codes (201, 204, 404, 409)

### 2. Input Validation
- Separate Request DTOs (CreateUserRequest, UpdateUserRequest)
- Custom validation annotations (@UniqueEmail)
- Name: Required, 2-50 characters
- Email: Required, valid format, unique
- Clear validation error messages

### 3. DTO Pattern with MapStruct
- Separation of concerns
- Type-safe mapping between entities and DTOs
- API contracts independent of database schema
- Better security (no accidental data exposure)

### 4. Pagination Support
- Handles large datasets efficiently
- Customizable page size and sorting
- Production-ready scalability

### 5. Comprehensive Documentation
- Swagger UI for interactive testing
- OpenAPI 3.0 specification
- Detailed endpoint descriptions

### 6. Clean Code with Lombok
- Reduced boilerplate code
- Auto-generated getters, setters, constructors
- Cleaner and more maintainable codebase

### 7. Monitoring & Health Checks
- Spring Actuator endpoints
- Health status: /actuator/health
- Metrics: /actuator/metrics

### 8. Unit Testing
- JUnit 5 test cases
- Mockito for mocking dependencies
- Test coverage for service layer

See [IMPROVEMENTS.md](IMPROVEMENTS.md) for detailed breakdown of all improvements.

## Additional Endpoints

### Health Check
```bash
curl http://localhost:8081/actuator/health
```

### Metrics
```bash
curl http://localhost:8081/actuator/metrics
```

## Running Tests
```bash
mvn test
```

## H2 Console
Access at: http://localhost:8081/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: (leave empty)
