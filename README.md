# Bus Booking System API
A Java & Spring Boot REST API for managing bus routes, schedules, user bookings, and authentication.  
Built with Spring Boot, PostgreSQL, JWT authentication, and Gradle.

## Features
**User Authentication** (Register/Login with JWT)  
**Bus Routes** (CRUD operations for bus routes)  
**Booking System** (Users can create, update, and delete their bookings)  
**Seat Management** (Prevents double booking for the same time & route)  
**Secure API** (JWT authentication for protected endpoints) 

## Tech Stack
-**Java 21**
- **Spring Boot 3.4.2**
- **Spring Security & JWT**
- **PostgreSQL** (Relational Database)
- **Gradle** (Build tool)

## **Getting Started**
### **1 Clone the Repository**

### **2 Set up database(PostgreSQL)**
- **In application.properties**
spring.application.name=Bus-Booking-Project
server.port=?

jwt.secret=?

spring.datasource.url=jdbc:postgresql://localhost:5432/BusDB
spring.datasource.username=?
spring.datasource.password=?
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

**Download the BusDBExport.sql file from the 'db' directory and restore it in postgresql to use the existing route when creating bookings**

### **3 Build & Run the application**
./gradlew bootRun

**Register a user**
POST /api/users/register
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "securepassword"
}

**Login & get JWT token**
POST /api/users/login
{
  "email": "john@example.com",
  "password": "securepassword"
}

Use the response as the token in the Authorization header for protected routes.

**Create a booking(using JWT token)**
POST /api/booking/create
{
  "routeId": 1,
  "departureScheduleId": 5,
  "arrivalScheduleId": 8,
  "bookingDate": "2025-02-15",
  "seatNumber": 3,
  "price": 2.0
}

**Get User's Bookings (Requires JWT)**
GET /api/booking/me

** Update a Booking (Requires JWT)**
PUT /api/booking/update/{id}

** Delete a Booking (Requires JWT)**
DELETE /api/booking/delete/{id}

**Bus Routes**
**Get All Routes**
GET /api/bus/routes

**Get a Route by ID**
GET /api/bus/routes/{id}

**Create a Bus Route**
POST /api/bus/routes

**Update a Route by ID**
PUT /api/bus/routes/{id}

**Delete a Route**
DELETE /api/bus/routes/{id}

**Security**
JWT Authentication required for booking-related endpoints.
Users can only modify their own bookings.
Admin role functionality will be added later.

**Future Improvements**
Frontend Interface(React)
Email notifications for bookings
Additional routes added

**Licence**
This project is open-source under the MIT Licence.

