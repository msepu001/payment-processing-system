Payment Processing System

A production-style backend payment processing REST API built with Java 21 and Spring Boot. The project demonstrates backend API development, relational database persistence, automated testing, containerization, and deployment to AWS.

Overview

The Payment Processing System provides REST endpoints for managing payment records through a layered backend architecture.

The application supports creating, retrieving, updating, deleting, and filtering payments while persisting payment data in PostgreSQL.

The project was built to practice and demonstrate modern backend engineering concepts including:

RESTful API design
Layered application architecture
Database persistence with JPA/Hibernate
Request validation and exception handling
Automated unit and API testing
Docker containerization
Cloud deployment with AWS
Secure cloud credential management
Tech Stack

Backend

Java 21
Spring Boot
Spring Data JPA
Hibernate
REST APIs

Database

PostgreSQL
Amazon RDS

Testing

JUnit
Mockito
Spring MockMvc

DevOps & Cloud

Docker
Docker Compose
AWS EC2
AWS RDS
AWS Secrets Manager
AWS IAM
Architecture

The application follows a layered architecture:

Controller → Service → Repository → PostgreSQL

Controller Layer — Handles HTTP requests and REST responses.
Service Layer — Contains application and payment business logic.
Repository Layer — Provides database access using Spring Data JPA.
Database Layer — Stores payment records in PostgreSQL.

For the AWS deployment, the application runs inside a Docker container on Amazon EC2 and connects to a PostgreSQL database hosted on Amazon RDS.

Database credentials are stored in AWS Secrets Manager and accessed by the EC2 instance through an IAM role, avoiding hard-coded AWS credentials and database passwords in the application source code.

Features
Create payment records
Retrieve payment information
Update existing payments
Delete payments
Filter payments by status
Request validation
Centralized exception handling
PostgreSQL persistence
Automated unit testing
REST API testing
Dockerized application environment
AWS cloud deployment
Secure database credential management
Testing

The project includes automated tests using:

JUnit for Java testing
Mockito for service-layer unit testing and dependency mocking
Spring MockMvc for REST API/controller testing

Testing covers core payment business logic and REST endpoint behavior.

Docker

The application is containerized using Docker and Docker Compose.

The containerized environment includes:

Multi-stage Docker builds
Environment-based configuration
Container networking
Health checks
Persistent database storage for local development
AWS Deployment

The application has been deployed to AWS using:

Client → EC2 → Docker → Spring Boot → Amazon RDS

AWS services currently used include:

Amazon EC2 — Hosts the Dockerized Spring Boot application
Amazon RDS — Hosts the PostgreSQL database
AWS Secrets Manager — Stores database credentials securely
AWS IAM — Allows the EC2 instance to access required AWS resources without storing AWS access keys in the application
Project Goals

This project is being developed as an end-to-end backend engineering project focused on building and operating a production-style Java application.

Future improvements will include additional cloud monitoring, CI/CD automation, and continued production-readiness improvements.
