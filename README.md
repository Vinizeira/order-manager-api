# 🛒 Order Manager API

> A RESTful API built with Java and Spring Boot for managing orders, products, clients, and categories. Designed to serve any type of local commerce with real-time stock control, automatic order total calculation, and status tracking.

<p align="left">
  <img src="https://img.shields.io/badge/Java-21-red?style=for-the-badge" alt="Java 21" />
  <img src="https://img.shields.io/badge/Spring%20Boot-4.0.6-brightgreen?style=for-the-badge" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/PostgreSQL-18-blue?style=for-the-badge" alt="PostgreSQL" />
  <img src="https://img.shields.io/badge/Maven-Wrapper-orange?style=for-the-badge" alt="Maven" />
  <img src="https://img.shields.io/badge/Status-In%20Progress-yellow?style=for-the-badge" alt="Status" />
</p>

---

## 📌 About the Project

This project was built independently as part of my backend development journey with Java and Spring Boot.

The goal is to go beyond tutorials and build something that solves a real problem: helping local businesses manage their products, clients, and orders without relying on spreadsheets or paper notes.

Every decision in this project — from architecture to business rules — was made intentionally and can be explained.

---

## 🚀 Features

### Implemented

- 📦 Product management with category and stock control
- 👤 Client registration with CPF and phone formatting
- 🗂️ Category management
- 🧾 Order creation with multiple items
- 🔄 Order status update flow
- 💰 Automatic total calculation (`quantity × unit price`)
- 📉 Automatic stock deduction on order creation
- ♻️ Automatic stock return when an order is cancelled
- 🚫 Block orders when stock is insufficient
- ✅ Input validation with clear error messages
- 🔍 Filters by status, client, date range, category and low stock
- ⚠️ Standardized error responses with proper HTTP status codes
- 🔒 Sensitive data protected via environment variables
- 🔐 Authentication with Spring Security and JWT
- 👥 Role-based access control with `ROLE_USER` and `ROLE_ADMIN`
- 🔑 Password encryption with BCrypt
- 🧑‍💼 Initial admin user created by environment configuration
- 📖 Swagger/OpenAPI documentation with Bearer Token support

### Possible Future Improvements

- 📊 Sales reports
- 🐳 Docker and deployment

---

## 🏗️ Architecture

The project follows a layered architecture with clear separation of responsibilities:

```text
HTTP Request
│
├── Controller Layer   → Receives request, delegates to service, returns response
├── Service Layer      → Business logic and rules
├── Repository Layer   → Database operations via Spring Data JPA
├── Model Layer        → Entities mapped to database tables
├── DTO Layer          → Controls what enters and exits the API
└── Exception Layer    → Global error handling with standardized responses
```

---

## 📦 Project Structure

```text
src/
└── main/
    └── java/
        └── dev/projectx/order_manager_api/
            ├── controller/
            │   ├── CategoriaController.java
            │   ├── ClienteController.java
            │   ├── PedidoController.java
            │   └── ProdutoController.java
            ├── dto/
            │   ├── CategoriaRequest.java
            │   ├── CategoriaResponse.java
            │   ├── ClienteRequest.java
            │   ├── ClienteResponse.java
            │   ├── ItemPedidoRequest.java
            │   ├── PedidoRequest.java
            │   ├── PedidoResponse.java
            │   ├── ProdutoRequest.java
            │   └── ProdutoResponse.java
            ├── exception/
            │   ├── BusinessException.java
            │   ├── GlobalExceptionHandler.java
            │   └── ResourceNotFoundException.java
            ├── model/
            │   ├── Categoria.java
            │   ├── Cliente.java
            │   ├── ItemPedido.java
            │   ├── Pedido.java
            │   ├── Produto.java
            │   └── StatusPedido.java
            ├── repository/
            │   ├── CategoriaRepository.java
            │   ├── ClienteRepository.java
            │   ├── ItemPedidoRepository.java
            │   ├── PedidoRepository.java
            │   └── ProdutoRepository.java
            ├── service/
            │   ├── CategoriaService.java
            │   ├── ClienteService.java
            │   ├── PedidoService.java
            │   └── ProdutoService.java
            └── OrderManagerApiApplication.java
```

---

## 🔗 Endpoints

### Autenticação

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | `/auth/register` | Public | Register a regular user |
| POST | `/auth/login` | Public | Authenticate and return a JWT |

### Usuários

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/usuarios` | ADMIN | List all users |
| PATCH | `/usuarios/me/senha` | USER / ADMIN | Change authenticated user's password |

### Categorias

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/categorias` | USER / ADMIN | List all categories |
| GET | `/categorias/{id}` | USER / ADMIN | Get category by id |
| POST | `/categorias` | ADMIN | Create category |
| DELETE | `/categorias/{id}` | ADMIN | Delete category |

### Produtos

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/produtos` | USER / ADMIN | List all products |
| GET | `/produtos/{id}` | USER / ADMIN | Get product by id |
| GET | `/produtos/categoria/{categoriaId}` | USER / ADMIN | Filter products by category |
| GET | `/produtos/estoque-baixo?quantidade={n}` | ADMIN | Filter products with low stock |
| POST | `/produtos` | ADMIN | Create product |
| DELETE | `/produtos/{id}` | ADMIN | Delete product |

### Clientes

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/clientes` | ADMIN | List all clients |
| GET | `/clientes/{id}` | ADMIN | Get client by id |
| POST | `/clientes` | ADMIN | Create client |
| DELETE | `/clientes/{id}` | ADMIN | Delete client |

### Pedidos

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/pedidos` | ADMIN | List all orders |
| GET | `/pedidos/status/{status}` | ADMIN | Filter orders by status |
| GET | `/pedidos/cliente/{clienteId}` | ADMIN | Filter orders by client |
| GET | `/pedidos/periodo?inicio={date}&fim={date}` | ADMIN | Filter orders by date range |
| POST | `/pedidos` | USER / ADMIN | Create order |
| PATCH | `/pedidos/{id}/status` | ADMIN | Update order status |

---

## 🔐 Authentication and Authorization

The API uses JWT Bearer Token authentication.

Public routes:

- `POST /auth/register`
- `POST /auth/login`
- Swagger/OpenAPI routes

Protected routes must receive the token in the `Authorization` header:

```http
Authorization: Bearer your_jwt_token_here
```

Default access rules:

- `ROLE_USER`: can view product/category catalog and create orders.
- `ROLE_ADMIN`: can manage products, categories, clients, orders and users.

The development admin user is created automatically if it does not exist:

```text
email: admin@admin.com
password: admin123
```

For production, change `ADMIN_PASSWORD` and `JWT_SECRET` using environment variables before running the application.

## ⚠️ Error Responses

All errors return a standardized JSON response:

```json
{
    "timestamp": "2026-05-29T19:00:00",
    "status": 404,
    "error": "Not Found",
    "message": "Cliente não encontrado"
}
```

| Status | When |
|--------|------|
| 400 | Validation error or business rule violation |
| 401 | Missing, invalid or expired token |
| 403 | Authenticated user does not have permission |
| 404 | Resource not found |
| 500 | Unexpected internal error |

---

## 💡 Business Rules

- Every order starts with status `PENDENTE`
- Stock is automatically deducted when an order is created
- Order status can follow the flow `PENDENTE → PAGO → ENVIADO → ENTREGUE`
- Orders can be cancelled before delivery
- Cancelled orders return their items to stock
- Delivered or cancelled orders cannot be changed
- Orders with insufficient stock are blocked with a clear error message
- Order total is calculated automatically from items
- CPF and phone are stored as digits only, formatted on response
- Email is stored in lowercase to prevent duplicates

---

## ▶️ How to Run

### Prerequisites

- Java 21+
- PostgreSQL
- Maven

### Setup

Clone the repository:

```bash
git clone https://github.com/Vinizeira/order-manager-api.git
cd order-manager-api
```

Create the database:

```sql
CREATE DATABASE order_manager;
```

Set environment variables:

```bash
DB_URL=jdbc:postgresql://localhost:5432/order_manager
DB_USER=your_username
DB_PASSWORD=your_password
JWT_SECRET=change-me-use-a-long-secret-with-at-least-48-chars
JWT_EXPIRATION=3600000
ADMIN_EMAIL=admin@admin.com
ADMIN_PASSWORD=change-me-before-production
```

There is a `.env.example` file with the expected variables.

Run the application:

```bash
./mvnw spring-boot:run
```

### Running with Docker

You can also run the API and PostgreSQL together with Docker Compose:

```bash
docker compose up --build
```

The API will be available at:

```text
http://localhost:8081
```

Swagger UI:

```text
http://localhost:8081/swagger-ui/index.html
```

The PostgreSQL container is exposed on port `5433` to avoid conflicts with a local PostgreSQL installation:

```text
localhost:5433
```

Default development admin:

```text
email: admin@admin.com
password: admin123
```

To stop the containers:

```bash
docker compose down
```

To remove the database volume as well:

```bash
docker compose down -v
```

---

## 🛠️ Technologies

- Java 21
- Spring Boot 4.0.6
- Spring Data JPA
- Spring Validation
- PostgreSQL 18
- Hibernate 7
- Maven
- Git / GitHub
- IntelliJ IDEA
- Postman

---

## 🗺️ Development Roadmap

### Phase 1 — Setup ✅
- [x] Project created with Spring Initializr
- [x] PostgreSQL connected via environment variables
- [x] Package structure defined

### Phase 2 — Entities and Database ✅
- [x] `Categoria` entity
- [x] `Produto` entity with stock
- [x] `Cliente` entity with CPF and phone
- [x] `Pedido` entity with status
- [x] `ItemPedido` entity
- [x] `StatusPedido` enum
- [x] All tables created automatically via JPA

### Phase 3 — Repositories and CRUD ✅
- [x] Repositories for all entities
- [x] Services with `list`, `findById`, `save`, `delete`
- [x] Controllers with `GET`, `POST`, `DELETE` endpoints
- [x] Tested via Postman

### Phase 4 — DTOs and Validations ✅
- [x] Request and Response DTOs for all entities
- [x] Validations with `@NotBlank`, `@NotNull`, `@Positive`, `@Email`, `@CPF`
- [x] Controllers and services adapted to use DTOs
- [x] CPF and phone normalized on save, formatted on response

### Phase 5 — Orders and Business Rules ✅
- [x] Order creation with multiple items
- [x] Automatic total calculation
- [x] Automatic stock deduction
- [x] Block orders with insufficient stock

### Phase 6 — Filters and Queries ✅
- [x] Filter orders by status
- [x] Filter orders by client
- [x] Filter orders by date range
- [x] Filter products by category
- [x] Filter products with low stock

### Phase 7 — Error Handling ✅
- [x] Custom exceptions (`ResourceNotFoundException`, `BusinessException`)
- [x] Global exception handler with `@RestControllerAdvice`
- [x] Standardized error responses with HTTP status

### Phase 8 — Documentation 🔲
- [x] Swagger / OpenAPI integration
- [x] Annotated endpoints
- [x] README completed

### Phase 9 — Security 🔲
- [x] Spring Security + JWT
- [x] User registration and login
- [x] Protected routes
- [x] Role-based access (`ADMIN`, `USER`)
- [x] JSON responses for `401` and `403`
- [x] Admin initialization by environment configuration

### Phase 10 — Order Status ✅
- [x] Endpoint to update order status
- [x] Status transition validation
- [x] Stock return when an order is cancelled
- [x] Block changes to delivered or cancelled orders

### Phase 11 — Docker ✅
- [x] Dockerfile
- [x] Docker Compose with API + PostgreSQL
- [x] Docker instructions in README

---

## 📈 Learning Goals

This project is used to practice:

- RESTful API design
- Layered architecture
- Object-Oriented Programming
- Spring Boot ecosystem
- JPA and relational databases
- Input validation and error handling
- Security with JWT
- Docker and deployment
- Git workflow and versioning

---

## 🧠 Development Approach

```text
Understand the concept → Apply in the project → Test → Refactor → Evolve
```

---

## 👨‍💻 Author

**Vinicius Pereira**

Backend Developer — Java & Spring Boot
