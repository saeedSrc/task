# Project Name

Review System

## Overview

This project is a Java 22 Spring Boot application (Spring 3.3.5) that utilizes Maven for dependency management, Docker for containerization, and MySQL and Redis for database and caching. The application includes automatic database setup, table creation, and dummy data insertion on startup. It runs on port `8888` and provides an API that can be accessed via Postman.

## Prerequisites

- **Java 22** (Ensure Java 22 is installed and configured on your system)
- **Maven** (For managing dependencies)
- **Docker** (To run MySQL and Redis containers)
- **Docker Compose** (To manage multi-container applications)

## Setup Instructions

### Step 1: Docker Setup

1. Ensure Docker and Docker Compose are installed on your machine.

2. Run Docker Compose to start the MySQL and Redis containers:

    ```bash
    docker-compose up -d
    ```

   This command will start both MySQL and Redis in detached mode. Make sure your `application.properties` configurations match the required network and credentials.

### Step 2: Application Configuration and Dependencies

1. Allow Maven to resolve dependencies from `pom.xml`. Maven will automatically download and configure all required packages.
2. Open the project in your preferred IDE and run the application on **port 8888**.

   **Note:** The application will automatically create the database schema, tables, and insert initial dummy data.

### Step 3: Testing the API

Once the application is running, you can access the API endpoints through Postman. The necessary configuration for API endpoints and usage instructions is provided in the Postman collection attached to this project.

## Author Information

- **Author**: Saeed Rasooli
- **Contact**: [saeedrasooli421@gmail.com](mailto:saeedrasooli421@gmail.com)

Thank you for using this project!
