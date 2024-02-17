# Frequency Word Analyzer - Backend
## Overview
This project is a backend implementation for a Frequency Word Analyzer REST API. It takes a plain text file from an AWS S3 bucket, analyzes the word frequencies, and returns the top K most frequent words.
## Technologies Used
* Java 17 - LTS version of java currently supporting to generate springboot application
* Spring Boot 3.3.2 (Latest stable version) - Simplifies the development of production-ready applications.
* AWS SDK for Java - Provides Java APIs for AWS services, used for S3 interaction.
* Springdoc OpenAPI - Generates OpenAPI documentation for the API.
* Lombok - Reduces boilerplate code in Java.
* JUnit 5 - Testing framework for Java.
* Mockito - Mocking framework for unit tests.
* AssertJ - Fluent assertions library for Java.
* Testcontainers - Provides lightweight, throwaway instances of AWS services.

## Spring Boot Features
* **spring-boot-starter-web**: Starter for building web, including RESTful, applications using Spring MVC.
* **spring-boot-starter-cache**: Starter for using Spring Framework’s caching support.
* **spring-boot-starter-validation**: Starter for using Java Bean Validation with Hibernate Validator.
* **spring-boot-starter-security**: Starter for using Spring Security.

## Setup
### Clone the repository:

   SSH

   ```
   
   git@github.com:DeepuGeorgeJacob/FreqWord.git
   
   ```
   
   Or (HTTP)
   
   ```
   
   https://github.com/DeepuGeorgeJacob/FreqWord.git
   
   ```
   
   Or (Git Cli)
   
   ```
    gh repo clone DeepuGeorgeJacob/FreqWord

   ```
### Configure AWS in Docker (Optional If you are not using AWS S3 Bucket)

If you have simulated AWS in a Docker image instead of using AWS directly, follow these steps:

* Start your Docker Desktop application.
* Navigate to the localstack-script folder using the command:
  ```
  
  cd aws/localstack-script

  ```
* Run docker containers. To do that follow the below command
  ```
  
  docker-compose up

  ```
