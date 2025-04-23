##  Overview
This project is a backend service for managing products and orders. It includes REST APIs to create orders, manage product inventory, and handle order status updates.

### 1. Prerequisites
- Java 17+
- Gradle 8.x
- Git (if cloning)
- IntelliJ IDEA (recommended)

### 3. Run the App

Or run `Application.java` directly from your IDE.

## Project Structure

```
src
├── main
│   ├── java/io/rohlik/gp/          # Main package
│   │   ├── order/                  # Order-related logic
│   │   └── product/                # Product-related logic
│   └── resources/
│       ├── application.yml         # App config
│       ├── schema.sql              # DB schema
│       └── data.sql                # Initial data
└── test

```
## DB credentials

use this url from browser : http://localhost:8080/h2-console

DB connect url will be available in console after running application

## Swagger 

browser url: http://localhost:8080/swagger-ui/index.html


