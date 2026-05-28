# EMS Backend — Employee Management System

Spring Boot REST API with JWT Security and Role Based Access Control.

## Tech Stack
- Java 21
- Spring Boot 3.5.x
- Spring Security + JWT
- MySQL
- Docker

## Prerequisites
- Docker installed
- MySQL running

## Quick Start

### Pull Image

```bash
docker pull pandimuneeswaran1206/ems-backend:latest
```

### Run Container

```bash
docker run -d \
  -p 8080:8080 \
  -e DB_URL=jdbc:mysql://host.docker.internal:3306/ems \
  -e DB_USERNAME=root \
  -e DB_PASSWORD=yourpassword \
  -e JWT_SECRET=yourjwtsecretkey \
  -e ADMIN_PASSWORD=youradminpassword \
  -e CORS_ORIGINS=http://localhost:3000 \
  --name ems-backend \
  pandimuneeswaran1206/ems-backend:latest
```

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| DB_URL | MySQL connection URL | jdbc:mysql://localhost:3306/ems |
| DB_USERNAME | MySQL username | root |
| DB_PASSWORD | MySQL password | **Required** |
| JWT_SECRET | JWT signing key | **Required** (min 32 chars) |
| ADMIN_PASSWORD | Default admin password | **Change in production!** |
| CORS_ORIGINS | Allowed frontend origins | http://localhost:3000 |

## API Endpoints

### Auth (Public)
| Method | URL | Description |
|--------|-----|-------------|
| POST | /api/auth/login | Login |
| POST | /api/auth/reset-password | Reset password |

### Employees (Protected)
| Method | URL | Role | Description |
|--------|-----|------|-------------|
| GET | /api/employees | ALL | Get all employees |
| GET | /api/employees/{id} | ALL | Get employee by id |
| POST | /api/employees | ADMIN | Create employee |
| PUT | /api/employees/{id} | ADMIN, MANAGER | Update employee |
| DELETE | /api/employees/{id} | ADMIN | Delete employee |

## Roles & Access

| Role | Permissions |
|------|-------------|
| ADMIN | Create, Read, Update, Delete |
| MANAGER | Read, Update (EMPLOYEE role only) |
| EMPLOYEE | Read own details only |

## Default Admin Credentials

```
Username : admin
Password : (set via ADMIN_PASSWORD env variable)
```

> ⚠️ Change default password immediately after first login!

## Notes
- First login forces password reset
- Employee delete is soft delete (isActive = false)
- JWT token expires in 10 hours