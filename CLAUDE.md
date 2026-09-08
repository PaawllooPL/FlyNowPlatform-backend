# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run

- **Compile**: `mvn clean compile`
- **Run the application**: `mvn spring-boot:run`
  - The app runs on `http://localhost:8080` (configured in `application.properties` and `ApiPathSegments.java`)
- **Install locally**: `mvn clean install`
- **Project structure** (Maven multi-module):
  - `flynow-domain` — pure domain models (entities, enums, value objects). No external deps beyond Lombok and jjwt.
  - `flynow-service` — business logic, services, mappers, exceptions, DTOs. Depends on `flynow-domain`.
  - `flynow-infrastructure` — JPA entities, Spring Data JPA repositories, DB access. Depends on `flynow-domain` and `flynow-service`.
  - `flynow-application` — Spring Boot entry point (`FlynowApplication.java`), initialization beans (`BeansInitializer.kt`), `application.properties`/`application.yml`.
  - `flynow-api` — REST controllers, Spring Security config, Swagger/OpenAPI. Depends on `flynow-service`.
- **Profiling**: `mvn spring-boot:run -Dspring-boot.profiles=dev` (dev profile uses `application.properties`; default uses `application.yml`)

## Architecture Overview

This is a **clean layered architecture** (domain → service → infrastructure → api) with **anemic DDD** (anemic domain-driven design):

- **`flynow-domain`**: Pure anemic domain models (data structures with Lombok annotations `@Getter`, `@Setter`, `@Builder`, `@AllArgsConstructor` — no embedded business behavior). Models include: `Flight`, `User`, `Role`, `Company`, `AircraftType`, `FlightPicture`, `Comment`, `Passenger`, `RoleEnum`, `VoivodeshipEnum`. No framework dependencies.
- **`flynow-service`**: 
  - Business logic services (`ImageService`, `TestService`, `UserServiceImpl`, `CompanyServiceImpl`, `OfferServiceImpl`, `CommentServiceImpl`)
  - Mappers (`UserMapper`, `CompanyMapper`, `RoleMapper`, `AircraftTypeMapper`, `CommentMapper`)
  - Custom exceptions (`FlightNotFoundException`, `SeatNotAvailableException`, `BadRequestException`, `UserNotFoundException`, `NotAuthenticatedException`, `NotAuthorizedException`, `InsufficientPermissionsException`, `AircraftTypeNotFoundException`, `CommentNotAllowedException`)
  - Use-case interfaces (`OfferUseCases`, `CommentUseCases`, `CompanyUseCases`, `UserUseCases`)
  - **Repository interfaces** (command and query) that define data access contracts:
    - *Command*: `FlightCommandRepository`, `AircraftTypeCommandRepository`, `FlightPictureCommandRepository`, `UserFlightCommandRepository`, `RoleCommandRepository`, `CommentCommandRepository`, `CompanyCommandRepository`, `UserCommandRepository`
    - *Query*: `FlightQueryRepository`, `CompanyQueryRepository`, `UserFlightQueryRepository`, `UserQueryRepository`, `AircraftTypeQueryRepository`
- **`flynow-infrastructure`**: 
  - JPA `@Entity` classes (mapped to PostgreSQL)
  - Repository implementations that implement service layer interfaces:
    - Command: `FlightCommandRepositoryImpl`, `AircraftTypeCommandRepositoryImpl`, `FlightPictureCommandRepositoryImpl`, `UserFlightCommandRepositoryImpl`, `CommentCommandRepositoryImpl`, `CompanyCommandRepositoryImpl`, `UserCommandRepositoryImpl`, `RoleCommandRepositoryImpl`
    - Query: `FlightQueryRepositoryImpl`, `CompanyQueryRepositoryImpl`, `UserFlightQueryRepositoryImpl`, `UserQueryRepositoryImpl`, `AircraftTypeQueryRepositoryImpl`
  - Traditional Spring Data JPA repositories (`*JpaRepository`) are used only by the infrastructure implementations
- **`flynow-application`** — Spring Boot entry point (`FlynowApplication.java`), initialization beans (`BeansInitializer.kt`), properties/config files. Depends on `flynow-api`, `flynow-service`, `flynow-infrastructure`.
- **`flynow-api`**: REST controllers (`ImageController`, `OfferController`, `CommentController`, `CompanyController`, `TestController`, `AuthenticationController`), Spring Security config (`SecurityConfig`, `ApplicationConfig`, `JwtAuthenticationFilter`), Swagger/OpenAPI config (`SwaggerAdapter`, `SwaggerConfig`), JWT service (`JwtService`), and authentication provider. Uses `@RestControllerAdvice` (`GlobalExceptionHandler`) for structured error responses.

## Key Technical Details

- **Spring Boot 3.3.4** with Java 22
- **PostgreSQL** database (connection: `jdbc:postgresql://localhost:5432/FlyNowDatabase`, user: `postgres`, password: `postgres` in `application.properties`; `application.yml` has `ddl-auto: create`)
- **JWT authentication** using `io.jsonwebtoken:jjwt` (HS256 with base64-secret). Secret key from `jwt.secretKey` property (`Jj6ph7a1mMDbd16VnpXlJjh93nr8tP6F5ayZDRcGAiY=`).
- **Stateless session management**: `SessionCreationPolicy.STATELESS`
- **CORS**: Configured for `http://localhost:4200` (Angular front-end origin)
- **Spring Security filters**: `JwtAuthenticationFilter` before `UsernamePasswordAuthenticationFilter`
- **Password hashing**: `BCryptPasswordEncoder`
- **Image storage**: Filesystem-based (`image.upload.path=..//images` from `application.properties`), served via `ImageService`
- **Lombok** used extensively for boilerplate reduction
- **No in-code tests** observed; `TestService` is used for database seeding in `DataLoader`

## Common Development Tasks

| Task | Command |
|---|---|
| Compile project | `mvn clean compile` |
| Run the app | `mvn spring-boot:run` |
| Run all tests | `mvn test` |
| Generate IDEA project | `mvn idea:idea` |
| Checkstyle/quality | Check config — no dedicated plugin found in pom.xml |
| Seed/test database | `TestService` provides `CreateRoles`, `CreateAircraftTypes`, `CreateUsers`, `CreateCompany`, `CreateAircraftTypes`, `SaveImage` methods |
| Build Docker/image | Not configured in this repo |

## API Endpoints (high-level)

- `GET /api/v1/offers` — list offer previews
- `GET /api/v1/offers/filtered` — filtered by voivodeships
- `GET /api/v1/offers/{flightId}/details` — offer details with comments
- `POST /api/v1/offers/buy/{flightId}` — buy an offer
- `POST /api/v1/offers/create` — create offer (multipart/form-data with image)
- `GET /api/v1/offers/user-offers` — user's offers
- `GET /api/v1/offers/organizer-offers` — organizer's offers
- `POST /api/v1/authentication/login` — login
- `POST /api/v1/authentication/register` — register
- `GET /api/v1/image/{filename}` — serve stored image
- `POST /api/v1/comments/add` — add comment
- `GET /api/v1/company/create` — create company

## Security

- All auth endpoints (`/authentication/login`, `/authentication/register`, `/api/v1/offers`, `/api/v1/offers/details`, `/api/v1/offers/buy`) are **permitAll**
- Role-based access:
  - `user` role: access user-specific offers and certain create/edit endpoints
  - `organizer` role: organize offers/comments
  - `admin` role: full access
- JWT token must be sent as `Authorization: Bearer <token>` header
- Token expiry: 15 minutes (access), 6 hours (refresh)

## Error Handling

Global exception handler (`GlobalExceptionHandler`) maps specific exceptions to HTTP status codes:

| Exception | HTTP Status | Body |
|---|---|---|
| `BadRequestException` | 400 | "Incorrect request" |
| `NotAuthenticatedException` | 401 | "Not authenticated" |
| `NotAuthorizedException` | 403 | "Not authorized." |
| `InsufficientPermissionsException` | 403 | exception message |
| `UserNotFoundException` | 404 | "User not found." |
| `CompanyNotFoundException` | 404 | "Company not found." |
| `FlightNotFoundException` | 404 | "Flight not found." |
| `AircraftTypeNotFoundException` | 404 | "Aircraft type not found." |
| `CommentNotAllowedException` | 403 | "Comment not allowed." |
| `SeatNotAvailableException` | 410 | "Seat not available" |
| `RuntimeException / Exception` | 500 | — |

## Front-end Note

CORS is configured for `http://localhost:4200` — ensure any development front-end uses this origin or the CORS config will need updating in `SecurityConfig.java`.