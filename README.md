# Task Management System

## Overview
The Task Management System is a web application built using Java, Spring Boot, and MySQL, designed to help users manage their tasks effectively. It offers features like user authentication, task creation, updating, deletion, and a history tracking system for tasks.

## Features
- **User Authentication**: Secure login and registration for users.
- **Task Management**: Create, update, and delete tasks with ease.
- **Task Filtering**: Search for tasks based on title and status.
- **Task History**: Track changes made to tasks over time.
- **Role-Based Access Control**: Differentiate permissions between users and administrators.

## Technologies Used
- **Java**: Programming language for backend development.
- **Spring Boot**: Framework for building Java applications.
- **MySQL**: Database for storing user and task information.
- **Spring Security**: For securing the application with JWT-based authentication.

## Installation
To set up the project locally, follow these steps:

1. Clone the repository:
   ```bash
   git clone git@github.com:mahmoud-gomaa7/task-management-system.git
2. Navigate to the Project Directory:
   cd task-management-system
3. Set Up the MySQL Database:
  spring.datasource.url=jdbc:mysql://localhost:3306/task_management_db
  spring.datasource.username=your_username
  spring.datasource.password=your_password
  spring.jpa.hibernate.ddl-auto=update
4.Install Dependencies:
  mvn install
5.Run the Application:
  mvn spring-boot:run
6.Access the Application:
  http://localhost:8080
7.Testing the API:
  Register a new user:
    POST /auth/register
  Login a user:
    POST /auth/login
  Get all tasks for the current user:
    GET /tasks




