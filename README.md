# springboot-microservice-task-ananda-muhammad-hafiz


* How to Run the project 
    1. Create the database
    CREATE DATABASE books_management;
    if spring.jpa.hibernate.ddl-auto = update is enabled, the books table is created automatically on startup. To create it manually instead:
    CREATE TABLE books (
        book_id        BIGSERIAL PRIMARY KEY,
        isbn           VARCHAR(20)  NOT NULL,
        title          VARCHAR(255) NOT NULL,
        author         VARCHAR(255) NOT NULL,
        published_date DATE         NOT NULL
    );

    Note: published_date must be of type DATE (not TIMESTAMP). Hibernate's ddl-auto=update does not change the type of existing columns, so alter it manually if needed: ALTER TABLE books ALTER COLUMN published_date TYPE date USING published_date::date;

    2. Build and Run
    - Windows (powershell)
        .\mvnw.cmd clean install -DskipTests
        .\mvnw.cmd spring-boot:run
    - Package JAR
        java -jar target/book-management-0.0.1-SNAPSHOT.jar

    Note : The application starts at http://localhost:8073 by default.

* Environment Variables
    The application reads its configuration from environment variables, with defaults for local development.
    SERVER_PORT = 8073
    DB_URL = jdbc:postgresql://localhost:5432/book_management
    DB_USERNAME = postgres
    DB_PASSWORD = postgres
    JPA_DDL_AUTO = update
    These map to src/main/resources/application.properties:

    spring.application.name=book-management


    # DATABASE CONFIG
    spring.datasource.driver-class-name = org.postgresql.Driver

    spring.datasource.url = jdbc:postgresql://localhost:5432/books
    spring.datasource.username = postgres
    spring.datasource.password = postgres

    spring.datasource.hikari.minimumIdle = 1
    spring.datasource.hikari.maximumPoolSize = 5
    spring.datasource.hikari.pool-size=30

    spring.jpa.hibernate.ddl-auto = none


    # SERVER CONFIG
    server.port=8073
    server.address=0.0.0.0

* Sample postman request or collection
    Base URL : http://localhost:8073

    Field Rules :
    isbn -> Required on create and update
    title -> Required on create and update
    author -> Required on create and update
    publishedDate -> Required on create and update
    pageNumber -> Start at 1

    1. Create
    Method : POST
    Endpoint : /api/books
    Request : 
        {
            "isbn": "9780132350884",
            "title": "Testing",
            "author": "Ananda Muhammad Hafiz",
            "publishedDate": "2026-10-04"
        }
    Response : 
        {
            "data": {
                "bookId": 1,
                "title": "Testing",
                "author": "Ananda Muhammad Hafiz",
                "isbn": "9780132350884",
                "publishedDate": "2026-10-04"
            },
            "message": "Create book successfully",
            "status": "SUCCESS"
        }
    2. Find by ID
    Method : GET
    Endpoint : /api/books/${id}
    Response :
        {
            "data": {
                "bookId": 1,
                "title": "Testing",
                "author": "Ananda Muhammad Hafiz",
                "isbn": "9780132350884",
                "publishedDate": "2026-10-04"
            },
            "message": "Book found successfully",
            "status": "SUCCESS"
        }
    3. Update
    Method : PUT
    Endpoint : /api/books/${id}
    Request : 
        {
            "title": "Testing",
            "author": "Ananda Muhammad Hafiz",
            "isbn": "A-001",
            "publishedDate": "2026-10-04"
        }
    Response : 
        {
            "data": {
                "bookId": 1,
                "title": "Testing",
                "author": "Ananda Muhammad Hafiz",
                "isbn": "A-001",
                "publishedDate": "2026-10-04"
            },
            "message": "Book updated successfully",
            "status": "SUCCESS"
        }
    4. Patch
    Method : PATCH
    Endpoint : /api/books/${id}
    Request :
        {
            "title": "Testing PATCH"
        }
    Response :
        {
            "data": {
                "bookId": 1,
                "title": "Testing PATCH",
                "author": "Ananda Muhammad Hafiz",
                "isbn": "A-001",
                "publishedDate": "2026-10-04"
            },
            "message": "Book patched successfully",
            "status": "SUCCESS"
        }
    5. Find all (Inquiry) with pagination
    Method : POST
    Endpoint : /api/books/inquiry
    Request :
        {
            "pageNumber": 1,
            "pageSize": 10,
            "title": "",
            "author": "",
            "isbn": "a",
            "publishedDate": "2026-10-04"
        }
    Response : 
        {
            "data": {
                "pageNumber": 1,
                "pageSize": 10,
                "totalDataInPage": 1,
                "totalData": 1,
                "totalPages": 1,
                "data": [
                    {
                        "bookId": 1,
                        "title": "Testing PATCH",
                        "author": "Ananda Muhammad Hafiz",
                        "isbn": "A-001",
                        "publishedDate": "2026-10-04"
                    }
                ]
            },
            "message": "Inquiry book successfully",
            "status": "SUCCESS"
        }
    6. Delete
    Method : DELETE
    Endpoint : /api/books/${id}
    Response : 
        {
            "data": null,
            "message": "Book deleted successfully",
            "status": "SUCCESS"
        }