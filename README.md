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
- 💰 Automatic total calculation (`quantity × unit price`)
- 📉 Automatic stock deduction on order creation
- 🚫 Block orders when stock is insufficient
- ✅ Input validation with clear error messages
- 🔍 Filters by status, client, date range, category and low stock
- ⚠️ Standardized error responses with proper HTTP status codes
- 🔒 Sensitive data protected via environment variables

### Planned

- 🔄 Order status update (`PENDING → PAID → SHIPPED → DELIVERED → CANCELLED`)
- 📊 Sales reports
- 🔐 Authentication with Spring Security and JWT
- 📖 API documentation with Swagger
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

### Categorias

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/categorias` | List all categories |
| GET | `/categorias/{id}` | Get category by id |
| POST | `/categorias` | Create category |
| DELETE | `/categorias/{id}` | Delete category |

### Produtos

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/produtos` | List all products |
| GET | `/produtos/{id}` | Get product by id |
| GET | `/produtos/categoria/{categoriaId}` | Filter products by category |
| GET | `/produtos/estoque-baixo?quantidade={n}` | Filter products with low stock |
| POST | `/produtos` | Create product |
| DELETE | `/produtos/{id}` | Delete product |

### Clientes

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/clientes` | List all clients |
| GET | `/clientes/{id}` | Get client by id |
| POST | `/clientes` | Create client |
| DELETE | `/clientes/{id}` | Delete client |

### Pedidos

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/pedidos` | List all orders |
| GET | `/pedidos/status/{status}` | Filter orders by status |
| GET | `/pedidos/cliente/{clienteId}` | Filter orders by client |
| GET | `/pedidos/periodo?inicio={date}&fim={date}` | Filter orders by date range |
| POST | `/pedidos` | Create order |

---

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
| 404 | Resource not found |
| 500 | Unexpected internal error |

---

## 💡 Business Rules

- Every order starts with status `PENDING`
- Stock is automatically deducted when an order is created
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
DB_USERNAME=your_username
DB_PASSWORD=your_password
```

Run the application:

```bash
./mvnw spring-boot:run
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
- [ ] Spring Security + JWT
- [ ] User registration and login
- [ ] Protected routes
- [ ] Role-based access (`ADMIN`, `USER`)

### Phase 10 — Deploy 🔲
- [ ] Dockerfile
- [ ] Docker Compose with API + PostgreSQL
- [ ] Deploy on Railway or Render
- [ ] Public URL in README

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

This project evolves together with my learning process as a backend developer.

---

## 👨‍💻 Author

**Vinicius Pereira**

Backend Developer — Java & Spring Boot
