# Spring Boot Template

A complete Spring Boot template with scalable architecture, JWT authentication, entity auditing, error logging, and essential features for web applications.

## 🚀 Features

- ✅ **Spring Boot 3.5.9** - Latest stable version
- ✅ **JWT Authentication** - Complete authentication and authorization system
- ✅ **Layered Architecture** - Clear separation of concerns
- ✅ **Spring Security** - Complete security configuration
- ✅ **Spring Data JPA** - Data persistence with PostgreSQL
- ✅ **Entity Auditing** - Hibernate Envers for automatic entity change tracking
- ✅ **Error Logging** - Automatic error logging to database
- ✅ **Action Auditing** - Manual action logging system
- ✅ **Validations** - Data validation with Bean Validation
- ✅ **Exception Handling** - Global exception handling
- ✅ **OpenAPI/Swagger** - Automatic API documentation
- ✅ **Docker** - Complete Docker and Docker Compose setup
- ✅ **Hot Reload** - Automatic reload in development
- ✅ **Lombok** - Boilerplate code reduction
- ✅ **MapStruct** - Automatic object mapping
- ✅ **Spotless** - Automatic code formatting with Google Java Format
- ✅ **Git Hooks** - Automatic code formatting on commit

## 🛠️ Technologies

- **Java 17**
- **Spring Boot 3.5.9**
- **Spring Security**
- **Spring Data JPA**
- **PostgreSQL 17**
- **JWT (jjwt 0.12.6)**
- **Hibernate Envers** - Entity auditing
- **Lombok** - Code generation
- **MapStruct 1.6.2** - Object mapping
- **OpenAPI/Swagger (springdoc-openapi 2.6.0)** - API documentation
- **Spotless 8.1.0** - Code formatting
- **Docker & Docker Compose** - Containerization

## 📋 Prerequisites

- Java 17 or higher
- Docker and Docker Compose
- Gradle (included with the project)

## 📁 Project Structure

```
src/main/java/com/andrewpg/springboottemplate/
├── config/                    # Configuration classes
│   ├── dev/                   # Development-specific configs
│   │   └── DataInitializer.java
│   ├── security/              # Security configuration
│   │   ├── Path.java          # Centralized route definitions
│   │   └── SecurityConfig.java
│   ├── EnversRevisionListener.java  # Envers revision listener
│   ├── JpaAuditingConfig.java # JPA auditing configuration
│   ├── OpenAPIConfig.java     # Swagger/OpenAPI configuration
│   └── WebMvcConfig.java      # Web MVC configuration
│
├── controller/                # REST Controllers
│   ├── implementation/        # Controller implementations
│   │   ├── AuthController.java
│   │   └── UserController.java
│   ├── IAuthController.java  # Controller interfaces
│   └── IUserController.java
│
├── dto/                       # Data Transfer Objects
│   ├── request/               # Request DTOs
│   │   ├── LoginRequest.java
│   │   └── RegisterRequest.java
│   └── response/              # Response DTOs
│       ├── ApiResponse.java   # Standard API response wrapper
│       ├── JwtResponse.java
│       └── UserResponse.java
│
├── entity/                    # JPA Entities
│   ├── AuditLog.java          # Action audit log entity
│   ├── CustomRevisionEntity.java  # Envers revision entity
│   ├── ErrorLog.java          # Error log entity
│   ├── Role.java
│   └── User.java
│
├── exception/                 # Exception handling
│   ├── base/                  # Base exception classes
│   │   ├── AuditableException.java
│   │   ├── BusinessException.java
│   │   ├── CriticalException.java
│   │   └── ErrorSeverity.java
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
│
├── mapper/                    # MapStruct mappers
│   ├── AuthMapper.java
│   └── UserMapper.java
│
├── repository/                # JPA Repositories
│   ├── AuditLogRepository.java
│   ├── ErrorLogRepository.java
│   ├── RoleRepository.java
│   └── UserRepository.java
│
├── security/                  # Security components
│   ├── AuthUser.java          # @AuthUser annotation
│   ├── AuthUserArgumentResolver.java
│   ├── JwtAuthenticationFilter.java
│   ├── JwtService.java
│   └── UserDetailsServiceImpl.java
│
└── service/                    # Business logic
    ├── implementation/        # Service implementations
    │   ├── AuditService.java
    │   ├── AuthService.java
    │   ├── RoleService.java
    │   └── UserService.java
    ├── IAuthService.java      # Service interfaces
    ├── IRoleService.java
    └── IUserService.java
```

## 🚀 Quick Start

### 1. Environment Variables

Create a `.env` file in the project root with the following variables:

```env
# Spring Profile
SPRING_PROFILES_ACTIVE=dev

# Database Development
DB_URL=jdbc:postgresql://postgres_db:5432/
DB_PORT=5432
DB_NAME=springboot_template
DB_USER=postgres
DB_PASSWORD=postgres
DB_DRIVER=org.postgresql.Driver
DB_PLATFORM=org.hibernate.dialect.PostgreSQLDialect

# Database Production
DB_PROD_URL=jdbc:postgresql://localhost:5432/
DB_PROD_PORT=5432
DB_PROD_NAME=springboot_template_prod
DB_PROD_USER=postgres
DB_PROD_PASSWORD=postgres
DB_PROD_DRIVER=org.postgresql.Driver
DB_PLATFORM=org.hibernate.dialect.PostgreSQLDialect

# JWT (optional, has default values)
JWT_SECRET=your_secure_secret_key_here_minimum_64_characters
JWT_EXPIRATION=86400000

# CORS (optional, defaults to *)
CORS_ALLOWED_ORIGINS=*
```

### 2. Initialize Repository

After cloning the repository, run the initialization script to set up Git hooks:

```bash
chmod +x scripts/init-repo.sh
./scripts/init-repo.sh
```

This script configures Git to use the hooks from `.githooks/` directory.

### 3. Build and Run with Docker

```bash
# Build the images
docker compose build

# Start the services
docker compose up

# Or in detached mode
docker compose up -d

# View logs
docker compose logs -f app

# Stop the services
docker compose down
```

### 4. Access the Application

- **API**: http://localhost:8080/api
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs

## 🐳 Docker Development Setup

### How It Works

The Docker setup is optimized for development with hot-reload capabilities:

1. **Base Image**: Uses `eclipse-temurin:17-jdk` as base image
2. **Volume Mounting**:
   - `./src:/app/src` - Source code for hot-reload
   - `./build:/app/build` - Build artifacts
3. **Continuous Compilation**: `docker-entrypoint.sh` runs `gradlew compileJava --continuous` in the background
4. **Hot Reload**: Spring DevTools automatically restarts the application when `.class` files change

### Docker Entrypoint Script

The `docker-entrypoint.sh` script:
- Compiles the project initially with `./gradlew compileJava`
- Runs continuous compilation in the background (`./gradlew compileJava --continuous`)
- Executes `bootRun` in the foreground with DevTools enabled
- Handles graceful shutdown on container stop (SIGTERM/SIGINT)

### Development Workflow

1. **Start containers**: `docker compose up`
2. **Edit code**: Modify files in `src/`
3. **Automatic reload**: Changes are compiled and the app restarts automatically
4. **View logs**: `docker compose logs -f app`

### Running Without Docker

```bash
# Compile
./gradlew build

# Run
./gradlew bootRun

# Run tests
./gradlew test
```

## 🔧 Git Hooks & Code Formatting

### Git Hooks Setup

The project includes Git hooks in the `.githooks/` directory that automatically format code before commits.

**Initial Setup:**

After cloning the repository, you must run the initialization script:

```bash
chmod +x scripts/init-repo.sh
./scripts/init-repo.sh
```

This script configures Git to use the hooks from `.githooks/` by setting `git config core.hooksPath .githooks`.

**Pre-commit Hook:**

The `.githooks/pre-commit` hook:
- Detects staged Java files (`.java` extension)
- Runs `./gradlew spotlessApply` to format the code
- Automatically adds formatted files back to staging
- Only processes files that are actually staged for commit

**How it works:**
1. When you run `git commit`, the pre-commit hook is triggered
2. It checks for staged Java files
3. If found, it runs Spotless to format them
4. Formatted files are automatically added to staging
5. Commit proceeds with formatted code

**Skip Hook Temporarily:**

If you need to skip the hook for a specific commit:

```bash
git commit --no-verify
```

**Note:** The hooks are not automatically installed. You must run `scripts/init-repo.sh` after cloning the repository.

### Spotless Configuration

**Format Code:**
```bash
./gradlew spotlessApply
```

**Check Format:**
```bash
./gradlew spotlessCheck
```

**Configuration:**
- **Java**: Google Java Format 1.23.0
  - Removes unused imports
  - Trims trailing whitespace
  - Ensures files end with newline
  - UNIX line endings (LF)
- **Gradle/Markdown**: 4 spaces, trim trailing whitespace
- **XML/Properties**: 4 spaces, trim trailing whitespace

## 📝 Code Conventions & Style

### Package Organization

- **Interfaces First**: Service and controller interfaces are in the root package
- **Implementations**: Concrete implementations in `implementation/` subpackage
- **Dependency Injection**: Organized by category with comments:
  ```java
  // Services
  private final UserService userService;

  // Repositories
  private final UserRepository userRepository;

  // Mappers
  private final UserMapper userMapper;
  ```

### Naming Conventions

- **Classes**: PascalCase (e.g., `UserService`, `AuthController`)
- **Interfaces**: Prefix with `I` (e.g., `IUserService`, `IAuthController`)
- **Methods**: camelCase (e.g., `getUserById`, `registerUser`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `PUBLIC_PATHS`, `ADMIN_PATHS`)
- **Packages**: lowercase (e.g., `com.andrewpg.springboottemplate.service`)

### Code Style

- **Comments**: All code comments and documentation in English
- **User Messages**: Error messages and API responses in Spanish (for end users)
- **Swagger Documentation**: API documentation in English (technical documentation)
- **Indentation**: 2 spaces for Java files
- **Line Length**: Follow Google Java Format guidelines

### Dependency Organization

Dependencies in classes should be organized with comments:

```java
@Service
@RequiredArgsConstructor
public class MyService {

  // Services
  private final OtherService otherService;

  // Repositories
  private final MyRepository repository;

  // Mappers
  private final MyMapper mapper;

  // Security
  private final PasswordEncoder passwordEncoder;
}
```

### Exception Handling

- Use custom exception classes from `exception.base` package
- All exceptions are automatically logged to `error_logs` table via `GlobalExceptionHandler`
- User-facing error messages in Spanish
- Technical details in logs (English)

## 🏗️ Architecture

### Application Layers

1. **Controller Layer**: Handles HTTP requests and responses
   - Uses interfaces for contracts
   - Implementations in `implementation/` package
   - Returns `ApiResponse<T>` wrapper for consistent responses

2. **Service Layer**: Contains business logic
   - Interface-based design
   - Transaction management with `@Transactional`
   - Uses MapStruct for entity-DTO mapping

3. **Repository Layer**: Data access via Spring Data JPA
   - Extends `JpaRepository<T, ID>`
   - Custom queries with `@Query` annotation

4. **Entity Layer**: Domain models (JPA entities)
   - Uses Lombok for boilerplate reduction
   - JPA Auditing for `createdAt`/`updatedAt` timestamps
   - Hibernate Envers for change tracking (`@Audited` annotation)

5. **DTO Layer**: Data transfer objects
   - Request DTOs in `dto.request`
   - Response DTOs in `dto.response`
   - Validation annotations for input validation

6. **Security Layer**: Security configuration and JWT
   - `@AuthUser` annotation for automatic user injection in controllers
   - JWT filter for authentication
   - Centralized route definitions in `Path.java`

7. **Config Layer**: Application configurations
   - Security, JPA, OpenAPI, Web MVC configurations

### Roles and Permissions

Default roles (automatically initialized via `DataInitializer`):
- `ROLE_USER` - Standard user
- `ROLE_ADMIN` - Administrator
- `ROLE_MODERATOR` - Moderator

### Route Configuration

Routes are centralized in `Path.java`:
- `PUBLIC_PATHS`: No authentication required
- `USER_PATHS`: Requires authentication (any role)
- `ADMIN_PATHS`: Requires `ROLE_ADMIN` role

This centralization makes it easy to manage and update route permissions.

### @AuthUser Annotation

The `@AuthUser` annotation allows automatic injection of the authenticated user in controller methods:

```java
@GetMapping("/me")
public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(@AuthUser User user) {
    // user is automatically loaded from security context
    return ResponseEntity.ok(ApiResponse.success(userMapper.toUserResponse(user)));
}
```

This is handled by `AuthUserArgumentResolver` which:
1. Extracts authentication from SecurityContext
2. Loads the full `User` entity from database
3. Injects it into the controller method parameter

## 📊 Auditing System

### Entity Auditing (Hibernate Envers)

Entities annotated with `@Audited` automatically track all changes:

- **Audited Entities**: `User`, `Role`
- **Audit Tables**: `users_audit`, `roles_audit` (created automatically)
- **Revision Info**: Stored in `revinfo` table with username and IP address
- **Revision Listener**: `EnversRevisionListener` captures user and IP on each change

**How it works:**
- Every insert, update, or delete on audited entities creates a record in the audit table
- The `revinfo` table stores metadata about who made the change and when
- You can query the full history of any entity

### Error Logging

All exceptions are automatically logged to `error_logs` table via `GlobalExceptionHandler`:
- Exception type and message
- Stack trace
- Request URI and method
- Authenticated user (if available)
- HTTP status code
- Timestamp
- Request headers (optional)

### Action Auditing

Manually log important business actions using `AuditService`:

```java
@Autowired
private AuditService auditService;

auditService.logAction(
    "USER_CREATED",
    "User",
    userId,
    "User created successfully",
    null,  // old value
    userJson  // new value
);
```

This creates a record in `audit_logs` table with:
- Action type
- Entity type and ID
- User who performed the action
- Description
- Old and new values
- Request metadata (URI, method, IP)

## 🧪 Development

### Hot Reload

The project is configured for automatic reload in development:
- Changes in `.java` files are compiled automatically (via continuous compilation)
- Spring DevTools restarts the application when `.class` files change
- Works seamlessly with Docker volume mounting

### Adding New Endpoints

1. Create DTO in `dto/request` or `dto/response`
2. Create method in corresponding `Service` interface
3. Implement in `Service` implementation class
4. Create endpoint in `Controller` interface
5. Implement in `Controller` implementation class
6. Document with Swagger annotations (`@Operation`, `@Tag`)

### Adding New Entities

1. Create entity in `entity/` package
2. Add `@Audited` if you want change tracking with Envers
3. Create repository in `repository/` package
4. Create service interface in `service/` package
5. Create service implementation in `service/implementation/`
6. Create controller interface in `controller/` package
7. Create controller implementation in `controller/implementation/`
8. Create mapper in `mapper/` package (if needed for DTO conversion)

### Using MapStruct

MapStruct automatically generates mapping code at compile time:

```java
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
    User toUser(RegisterRequest request);
}
```

The mapper is automatically registered as a Spring bean and can be injected.

### Using @AuthUser

To inject the authenticated user in a controller:

```java
@GetMapping("/profile")
public ResponseEntity<ApiResponse<UserResponse>> getProfile(@AuthUser User user) {
    // user is automatically loaded and injected
    return ResponseEntity.ok(ApiResponse.success(userMapper.toUserResponse(user)));
}
```

## 📦 Main Dependencies

- `spring-boot-starter-web` - Web framework
- `spring-boot-starter-security` - Security framework
- `spring-boot-starter-data-jpa` - JPA persistence
- `spring-boot-starter-validation` - Bean validation
- `jjwt` - JWT implementation
- `hibernate-envers` - Entity auditing
- `mapstruct` - Object mapping
- `springdoc-openapi` - API documentation
- `lombok` - Code generation
- `postgresql` - PostgreSQL driver
- `spotless` - Code formatting

## 🐛 Troubleshooting

### Docker Permission Issues

If you encounter permission errors with `gradlew`, the Dockerfile already includes `chmod +x gradlew`. If issues persist:

```bash
# Fix permissions
chmod +x gradlew docker-entrypoint.sh
```

### Database Connection Issues

1. Verify environment variables are correctly set in `.env` file
2. Ensure PostgreSQL container is running: `docker compose ps`
3. Check database logs: `docker compose logs database`
4. Verify network connectivity: `docker compose exec app ping postgres_db`

### Hot Reload Not Working

1. Ensure `./src` volume is mounted correctly in `docker-compose.yml`
2. Check `docker-entrypoint.sh` is executable
3. Verify Spring DevTools is enabled in `application-dev.properties`
4. Check container logs: `docker compose logs -f app`
5. Verify continuous compilation is running (check logs for "compileJava --continuous")

### Git Hooks Not Working

1. Verify hooks are installed: `git config core.hooksPath` should return `.githooks`
2. If not set, run: `./scripts/init-repo.sh`
3. Check hook is executable: `ls -la .githooks/pre-commit`
4. Make executable if needed: `chmod +x .githooks/pre-commit`

### Code Formatting Issues

```bash
# Apply formatting
./gradlew spotlessApply

# Check formatting
./gradlew spotlessCheck
```

If Spotless fails, ensure you have the correct Java version (17+) and Gradle is properly configured.

## 📄 License

This project is an open-source template.

## 👤 Author

AndrewPG

---

**Note**: This is a base template. Customize according to your specific needs.
