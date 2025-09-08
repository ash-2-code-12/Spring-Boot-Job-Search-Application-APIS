# Jobby - Job Search API

#### 🔗 Frontend Repository - [👉 View on GitHub](https://github.com/ash-2-code-12/React-Js-Jobby-job-search-application)
---

![Java](https://img.shields.io/badge/Java-21-informational?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-brightgreen?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-3.9-red?logo=apachemaven)

A secure RESTful API for managing job postings and user profiles, built with Spring Boot, PostgreSQL, and JWT authentication.

---

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [API Endpoints](#api-endpoints)
- [Setup & Installation](#setup--installation)
- [Authentication](#authentication)
- [Future Improvements](#future-improvements)
- [License](#license)

---

## Overview
The **Job Search API** is a backend application that provides secure endpoints for **job seekers and recruiters**.  
Users can register, log in, search for jobs, and apply, while recruiters can manage job postings.  
It is designed to be front-end ready with JSON responses for seamless integration.

---

## Features
- **JWT-based Authentication & Authorization**
- **User Roles**: Job Seeker & Recruiter
- **Job Postings CRUD** (create, update, delete, list)
- **Search Jobs** by title, location, and employment type
- **User Profile Management**
- **Secure API Endpoints** with Spring Security
- **Pagination & Sorting** for job listings
- **Exception Handling & Validation**

---

## Technologies Used
- **Java 21**
- **Spring Boot 3.5**
- **Spring Security + JWT**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **Lombok**
- **Swagger / OpenAPI** (for documentation)

---

## API Endpoints

### Auth
| Method | Endpoint           | Description                  |
|--------|------------------|------------------------------|
| POST   | `/auth/register`  | Register a new user          |
| POST   | `/auth/login`     | Login and receive JWT token  |

### Jobs
| Method | Endpoint                  | Description                            |
|--------|---------------------------|----------------------------------------|
| GET    | `/api/v1/jobs`            | Get all jobs with pagination & filters |
| GET    | `/api/v1/jobs/{id}`       | Get job details by ID                  |
| POST   | `/api/v1/jobs`            | Create a new job posting (recruiter)   |
| PUT    | `/api/v1/jobs/{id}`       | Update an existing job (recruiter)     |
| DELETE | `/api/v1/jobs/{id}`       | Delete a job posting (recruiter)       |

### Profiles
| Method | Endpoint                  | Description                            |
|--------|---------------------------|----------------------------------------|
| GET    | `/api/v1/profile`         | Get logged-in user profile             |
| PUT    | `/api/v1/profile`         | Update user profile                    |  

---

## Setup & Installation

1. **Clone the repository**
```bash
git clone https://github.com/your-username/job-search-api.git
```
2. **Create PostgreSQL database**
```bash
CREATE DATABASE jobsdb;
```
3. **Update credentials in application.properties**
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/jobsdb
spring.datasource.username=your_username
spring.datasource.password=your_password
```
4. **Build & Run**
```bash
mvn clean install
mvn spring-boot:run
```
## Access API
- Base URL: http://localhost:8081
- Swagger UI: http://localhost:8081/swagger-ui/index.html

---

##Authentication
- All job and profile endpoints require a Bearer JWT token.
- Obtain token via /auth/login.
  
| Method | Endpoint           | Description                  |
|--------|--------------------|------------------------------|
| POST   | `/auth/register`   | Register a new user          |
| POST   | `/auth/login`      | Login and receive JWT token  |

#### Sample Register
`POST /auth/register`
```json
{
  "username": "ashwin",
  "password": "password123",
  "role": "RECRUITER"
}
```
#### Sample Login

`POST /auth/login`
```json
{
  "username": "ashwin",
  "password": "password123"
}
```
#### Sample Response
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR..."
}
```
---
### Jobs
| Method | Endpoint            | Description                          |
|--------|---------------------|--------------------------------------|
| GET    | /api/v1/jobs        | Get all jobs with pagination & filters |
| GET    | /api/v1/jobs/{id}   | Get job details by ID                |
| POST   | /api/v1/jobs        | Create a new job posting (recruiter) |
| PUT    | /api/v1/jobs/{id}   | Update an existing job (recruiter)   |
| DELETE | /api/v1/jobs/{id}   | Delete a job posting (recruiter)     |

#### Sample Create Job

`POST /api/v1/jobs`
```json
{
  "title": "Backend Engineer",
  "companyName": "TechCorp",
  "location": "Bangalore",
  "employmentType": "Full Time",
  "packagePerAnnum": "15 LPA",
  "jobDescription": "We are looking for a skilled backend engineer with Spring Boot experience."
}
```
#### Response
```json
{
  "id": 1,
  "title": "Backend Engineer",
  "companyName": "TechCorp",
  "location": "Bangalore",
  "employmentType": "Full Time",
  "packagePerAnnum": "15 LPA",
  "jobDescription": "We are looking for a skilled backend engineer with Spring Boot experience."
}
```

#### Sample Get Jobs
`GET /api/v1/jobs?page=0&size=5&location=Bangalore`
```json
{
  "id": 1,
  "title": "Backend Engineer",
  "companyName": "TechCorp",
  "location": "Bangalore",
  "employmentType": "Full Time",
  "packagePerAnnum": "15 LPA",
  "jobDescription": "We are looking for a skilled backend engineer with Spring Boot experience.",
  "rating": 4
}
```
---
### Profiles
| Method | Endpoint          | Description                |
|--------|-------------------|----------------------------|
| GET    | /api/v1/profile   | Get logged-in user profile |
| PUT    | /api/v1/profile   | Update user profile        |

#### Sample Get Profile

`GET /api/v1/profile` with Auth header
#### Response
```json
{
  "id": 2,
  "username": "ashwin",
  "role": "JOB_SEEKER",
  "email": "ashwin@example.com",
  "skills": ["Java", "Spring Boot", "SQL"]
}
```
---
#### Sample Update Profile

`PUT /api/v1/profile` with Auth header
```json
{
  "email": "ashwin@example.com",
  "skills": ["Java", "Spring Boot", "ReactJS"]
}
```
#### Response
```json
{
  "id": 2,
  "username": "ashwin",
  "role": "JOB_SEEKER",
  "email": "ashwin@example.com",
  "skills": ["Java", "Spring Boot", "ReactJS"]
}
```
---
## Features in Development :
- Add **job application feature** for seekers to apply directly
- Implement **applicant tracking system** for recruiters
- Add **advanced search filters** (salary range, skills, experience level)
- Enable **email notifications** for new job postings and applications
- Add **role-based dashboards** for job seekers and recruiters
- Implement **soft delete** for job postings
- Add **unit and integration tests** for better reliability














