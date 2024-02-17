# Frequency Word Analyzer - Backend

## Overview

This project is a backend implementation for a Frequency Word Analyzer REST API. It takes a plain text file from an AWS S3 bucket, analyzes the word frequencies, and returns the top K most frequent words.


## Prerequisites

Before getting started with the Frequency Word Analyzer Backend, ensure that you have the following prerequisites installed on your machine:

* **Java 17**: The application is built with Java 17. You can download and install it from the [official Oracle website](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or use a compatible OpenJDK distribution.

* **Docker Desktop**: Docker is used for simulating AWS services and other dependencies. Install Docker Desktop from the [official Docker website](https://docs.docker.com/engine/install/).

Make sure to have these prerequisites in place before proceeding with the setup and usage of the application.

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

   ```shell
   
   git@github.com:DeepuGeorgeJacob/FreqWord.git
   
   ```
   
   Or (HTTP)
   
   ```shell
   
   https://github.com/DeepuGeorgeJacob/FreqWord.git
   
   ```
   
   Or (Git Cli)
   
   ```shell
    gh repo clone DeepuGeorgeJacob/FreqWord
   ```
### Configure AWS in Docker - Local AWS cloud  (Optional If you are not using AWS S3 Bucket from the AWS Cloud)

If you have simulated AWS in a Docker image instead of using AWS directly, follow these steps:

* Start your Docker Desktop application.
* Navigate to the localstack-script folder using the command:
    ```shell
    cd aws/localstack-script
    ```
* Make the `script.sh` executable.

    ```
    chmod +x script.sh
    ```
* Run docker containers. To do that follow the below command
    ``` shell
    docker-compose up
    ```
* Go back to root project file
    ```shell
    cd ../..
    ```

## Configure AWS CLI

**Step 1: Install AWS CLI**

Make sure you have the AWS CLI installed on your machine. You can download and install it from the [official AWS CLI website](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html).

**Step 2: Configure AWS CLI**

Open a terminal and run the following command:
```bash
    aws configure
  ```


This command will prompt you to enter the following information:

**AWS Access Key ID**: Your AWS access key.<br>
**AWS Secret Access Key**: Your AWS secret key.<br>
**Default region name**: The AWS region where your S3 bucket is located, Please use `eu-central-1` for this project.<br>
**Default output format**: You can leave this blank or choose a format (e.g., json).

**Step 3: Add AWS-related properties to your `application.properties` file**

It's important to note that storing AWS credentials directly in your `application.properties` file is not recommended for production environments. Instead, consider using environment variables or a secure configuration manager.

Here's an example of how you might structure your application.properties file:

```properties
    aws.accessKey=freqword
    aws.secretKey=freqword
    aws.bucket.name=freqwordbucket
    aws.s3.service.endpoint=http://s3.localhost.localstack.cloud:4566/
```

Replace `Your AWS Access Key ID` and `Your AWS Secret Access Key` with your actual AWS credentials.

**Step 4: Copy Files to S3 Bucket**

Now that your AWS CLI is configured, you can use the `aws s3 cp` command to copy files to an S3 bucket. The basic syntax is as follows:
```shell
  aws s3 cp <local-file-path> s3://<bucket-name>/<destination-path> --endpoint-url <url>
```

* `local-file-path`: The path to the file on your local machine.
* `bucket-name`: The name of your S3 bucket. In our case it should be `freqwordbucket`.
* `destination-path`: The path within the S3 bucket where you want to store the file.

For example, to copy a file named example.txt from your local machine to an S3 bucket named `freqwordbucket`:

```shell
  aws s3 cp example.txt s3://freqwordbucket --endpoint-url http://localhost:4566
```

I have added some files to our AWS folder. You can run the script to copy the files into AWS. 

Ensuring `copy_files_to_s3.sh` is executable.
```shell
  chmod +x ./aws/copy_files_to_s3.sh
```

Copy file to s3 bucket
```shell
  sh ./aws/copy_files_to_s3.sh
```

## Build and Run


To build the application, run the following command:
```shell
  ./gradlew build
```

This will compile the source code, run tests, and generate the executable JAR file.

To run the application, use the following command:
```shell
  java -jar build/libs/analyser-0.0.1-SNAPSHOT.jar
```

Ensure that you have Java installed on your machine. The application will be accessible at http://localhost:2000.

## API Documentation
Swagger documentation is available at http://localhost:2000/swagger-ui/index.html.

## Authentication and Request making


The application requires authentication to access certain endpoints. Use the following credentials:

**Username:** user <br>
**Password:** password <br>

**Example Usage:** Make a POST Request<Br>

To make a POST request to the frequency word analysis endpoint:

URL: http://localhost:2000/freq/word

Request Body

Include the following JSON payload in the request body:

```json
{
    "fileName": "3503.txt",
    "k": 5
}
```
* "fileName": The name of the text file to be analyzed.
* "k": The number of top words to retrieve.


**Example Response**

Upon a successful request, you will receive a JSON response containing the top K most frequent words along with their frequencies. For example:

 ```json
{
    "the": 70,
    "of": 56,
    "to": 56,
    "you": 47,
    "or": 43
}
```

## Conclusion
Thank you for exploring our Frequency Word Analyzer Backend! If you encounter any challenges or have questions regarding the configuration or usage of the application, feel free to reach out for assistance via [LinkedIn](https://se.linkedin.com/in/deepu-george-jacob-76753358).
