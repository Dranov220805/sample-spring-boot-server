# Sample Spring Boot Server (DevOps Testing)

This repository contains a lightweight Java Spring Boot application designed specifically for testing DevOps workflows. It is ideal for validating CI/CD pipelines, containerization strategies, and server deployments (such as AWS EC2 configurations).

## Configuration

This application requires specific environment variables to run correctly. You must create a `.env` file in the root directory of the project before starting the server.

Create a file named `.env` and add the following required variables:

```env
PORT=8080
MONGODB_URI=your_mongodb_connection_string_here
logging.level.root=INFO

## How to run

```
mvn clean spring-boot:run
