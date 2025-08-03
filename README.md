# HIV and Medical System Backend

## Project Description

This backend project provides APIs and services for a healthcare system aimed at enhancing access to HIV-related treatment and medical services. It includes secure authentication, patient appointment scheduling, ARV regimen management, anonymous consultations, treatment reminders, and doctor–patient interaction management. It also features admin dashboards and reporting capabilities for effective clinic management.

## Tech Stack

- **Language:** Java
- **Framework:** Spring Boot
- **Security:** Spring Security, JWT
- **ORM/Persistence:** JPA/Hibernate
- **Database:** MySQL
- **Cache:** Redis
- **Build Tool:** Maven

## Key Features

- **Secure RESTful APIs** (with validation, error handling, pagination)
- **JWT-based authentication** (password hashing, role-based access control)
- **Appointment scheduling**
- **Anonymous consultations**
- **Patient history and ARV regimen management**
- **Dashboard and reporting**

## Installation Guide (Docker Setup)

### Prerequisites

- Docker & Docker Compose installed.

### Step 1: Clone the Repository

```bash
git clone https://github.com/swp391-group-2/hiv-and-medical-system-backend.git
cd hiv-and-medical-system-backend
```

### Step 2: Create `docker-compose.yml`

Create a file named `docker-compose.yml` in the root folder with the following content:

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    container_name: mysql-container
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: medical_system
      MYSQL_USER: medicaluser
      MYSQL_PASSWORD: medicalpass
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

  redis:
    image: redis:latest
    container_name: redis-container
    ports:
      - "6379:6379"

volumes:
  mysql_data:
```

### Step 3: Start Docker Containers

```bash
docker-compose up -d
```

### Step 4: Configure the Application

Update your Spring Boot application's `application.yml` or `application.properties`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/medical_system
    username: medicaluser
    password: medicalpass

  jpa:
    hibernate.ddl-auto: update

redis:
  host: localhost
  port: 6379
```

### Step 5: Run the Application

Build and run using Maven:

```bash
mvn clean install
mvn spring-boot:run
```

Your backend service should now be running successfully!

## API Documentation

API documentation will be available at Swagger UI once the app is running:

```
http://localhost:8080/swagger-ui.html
```

---

## Contribution

Feel free to fork this repository, submit issues, and send pull requests!

---

## License

This project is licensed under the MIT License.

