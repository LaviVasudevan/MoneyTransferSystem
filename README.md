# Money Transfer System

A full-stack peer-to-peer money transfer demo application with an integrated **rewards program** — users earn points on qualifying transfers and redeem them for account balance credit.

## Tech Stack

| Layer      | Technology                          |
|------------|--------------------------------------|
| Backend    | Java 17, Spring Boot 3, Spring Data JPA, Spring Security |
| Frontend   | Angular 21                          |
| Database   | MySQL 8                             |
| API Docs   | springdoc-openapi (Swagger UI)      |

## Project Structure

```
MoneyTransferSystem/
├── backend/     Spring Boot REST API
├── frontend/    Angular single-page app
└── snowflake/   Optional analytics scripts (not required to run the app)
```

## Prerequisites

- Java 17+
- Node.js 18.19+ / 20.11+ / 22+
- MySQL 8.x
- Maven Wrapper is included (`mvnw` / `mvnw.cmd`) — no separate Maven install needed

## Getting Started

### 1. Database

Create the database and update credentials in `backend/src/main/resources/application.properties` if needed:

```sql
CREATE DATABASE MoneyTransferSystem;
```

Tables are auto-created on first run via `spring.jpa.hibernate.ddl-auto=update` — no manual schema setup required.

### 2. Backend

```bash
cd backend
./mvnw spring-boot:run
```

Runs on **http://localhost:8080**. Swagger UI: `http://localhost:8080/swagger-ui/index.html`.

The API is secured with a single HTTP Basic Auth user (configured in `application.properties`):
- Username: `2`
- Password: `pass123`

### 3. Frontend

```bash
cd frontend
npm install
npm start
```

Runs on **http://localhost:4200**.

## Features

### Money Transfer
- Transfer funds between accounts
- Transaction history per account
- Balance and account detail lookup

### Rewards Program
Users earn reward points on qualifying transfers and can redeem them for account balance.

**Earning rules:**
- Transaction must be successful
- Transfer amount must be greater than ₹100
- Sender and receiver must be different accounts
- **1 point per ₹100 transferred** (floored), credited to the **sender**

**Redemption:**
- **100 points = ₹5**, credited directly to the account balance (in-app only — no external withdrawal)

| Endpoint | Method | Description |
|---|---|---|
| `/api/v1/transfer` | POST | Transfer money between accounts |
| `/api/v1/accounts/{id}/rewards` | GET | Get reward summary (earned, redeemed, available, history) |
| `/api/v1/accounts/{id}/rewards/redeem` | POST | Redeem points for account balance credit |

## Running Tests

```bash
cd backend
./mvnw test
```

## Contributing

This project uses a feature-branch workflow:

```bash
git checkout -b feature/your-feature-name
# make changes
git add .
git commit -m "Add your feature"
git push -u origin feature/your-feature-name
```

Open a Pull Request against `main` for review before merging.
