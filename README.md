# MediConnect - Integrated Patient Care & Telehealth Management System

MediConnect is a comprehensive full-stack application designed to streamline patient care and facilitate telehealth services. It manages patient records, doctor consultations, appointments, and more, providing a seamless experience for both healthcare providers and patients.

## Project Structure

The project is divided into two main components:

- **MediConnect-Frontend**: The user interface built with Angular.
- **MediConnect-Backend**: The server-side application built with Spring Boot.

## Prerequisites

Before running the project, ensure you have the following installed:

- **Node.js & npm**: For running the Angular frontend.
- **Java 21**: The backend is built on Java 21.
- **Maven**: For building the Spring Boot backend.
- **MySQL Server**: The database used by the application.
- **Lombok Plugin**: If using an IDE (IntelliJ, Eclipse, VS Code), ensure the Lombok plugin is installed and annotation processing is enabled.

## Getting Started

### 1. Database Setup

1.  Start your MySQL server.
2.  Create a database named `mediconnect`.
    ```sql
    CREATE DATABASE mediconnect;
    ```
3.  Configure the database credentials in the backend application properties file located at `MediConnect-Backend/MediConnect-Backend/src/main/resources/application.properties`.
    Update the following lines with your MySQL username and password:
    ```properties
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    ```

### 2. Backend Setup (Spring Boot)

1.  Navigate to the backend directory:
    ```bash
    cd MediConnect-Backend/MediConnect-Backend
    ```
2.  Build the project using Maven:
    ```bash
    mvn clean install
    ```
3.  Run the application:
    ```bash
    mvn spring-boot:run
    ```
    Alternatively, you can run the application directly from your IDE (IntelliJ IDEA, Eclipse, VS Code) by running the main application class.

4.  The backend server will start on port `8081`.
    - **API Documentation (Swagger UI)**: Access the API documentation at [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)

### 3. Frontend Setup (Angular)

1.  Navigate to the frontend directory:
    ```bash
    cd MediConnect-Frontend
    ```
2.  Install the necessary dependencies:
    ```bash
    npm install
    ```
3.  Start the development server:
    ```bash
    ng serve
    ```
4.  Open your browser and navigate to [http://localhost:4200/](http://localhost:4200/). The application will automatically reload if you change any of the source files.

## Features

- **Patient Management**: Registration, medical history, and profile management.
- **Doctor Consultation**: Telehealth capabilities and prescription management.
- **Appointment Scheduling**: Easy booking and management of appointments.
- **Secure Authentication**: JWT-based authentication for secure access.

## Key Technologies

- **Frontend**: Angular 20+, HTML, CSS/SCSS.
- **Backend**: Spring Boot, Spring Data JPA, Spring Security.
- **Database**: MySQL.
