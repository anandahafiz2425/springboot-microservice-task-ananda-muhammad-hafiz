package com.springboot.book_management.exception;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.book_management.common.response.Response;
import com.springboot.book_management.common.response.ResponseStatus;

import jakarta.validation.ValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

        private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        @ExceptionHandler(ResponseStatusException.class)
        public ResponseEntity<Response<Object>> handleResponseStatusException(
                        ResponseStatusException ex) {

                log.warn(
                                "ResponseStatusException: status={}, reason={}",
                                ex.getStatusCode(),
                                ex.getReason());

                Response<Object> response = new Response<>(ResponseStatus.ERROR);

                response.setMessage(ex.getReason());
                response.setData(null);

                return ResponseEntity
                                .status(ex.getStatusCode())
                                .body(response);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Response<Object>> handleMethodArgumentNotValid(
                        MethodArgumentNotValidException ex) {

                String message = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .findFirst()
                                .map(error -> error.getDefaultMessage())
                                .orElse("Invalid request");

                log.warn("Validation error: {}", message);

                return buildErrorResponse(
                                HttpStatus.BAD_REQUEST,
                                message);
        }

        @ExceptionHandler(BindException.class)
        public ResponseEntity<Response<Object>> handleBindException(
                        BindException ex) {

                String message = ex.getBindingResult()
                                .getAllErrors()
                                .stream()
                                .findFirst()
                                .map(error -> error.getDefaultMessage())
                                .orElse("Invalid request");

                log.warn("Bind error: {}", message);

                return buildErrorResponse(
                                HttpStatus.BAD_REQUEST,
                                message);
        }

        @ExceptionHandler(ValidationException.class)
        public ResponseEntity<Response<Object>> handleValidationException(
                        ValidationException ex) {

                log.warn("ValidationException: {}", ex.getMessage());

                return buildErrorResponse(
                                HttpStatus.BAD_REQUEST,
                                ex.getMessage());
        }

        @ExceptionHandler(CannotGetJdbcConnectionException.class)
        public ResponseEntity<Response<Object>> handleDatabaseConnection(
                        CannotGetJdbcConnectionException ex) {

                log.error("Database connection error", ex);

                return buildErrorResponse(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                "Database is not accessible");
        }

        @ExceptionHandler(SQLException.class)
        public ResponseEntity<Response<Object>> handleSQLException(
                        SQLException ex) {

                log.error(
                                "SQL error: state={}, code={}",
                                ex.getSQLState(),
                                ex.getErrorCode(),
                                ex);

                return buildErrorResponse(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                "Terjadi kesalahan pada database");
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<Response<Object>> handleDataIntegrityViolation(
                        DataIntegrityViolationException ex) {

                log.error("Data integrity violation", ex);

                return buildErrorResponse(
                                HttpStatus.CONFLICT,
                                "Data cannot be processed due to data conflict");
        }

        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<Response<Object>> handleRuntimeException(
                        RuntimeException ex) {

                log.error("Runtime exception", ex);

                return buildErrorResponse(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                "An error occurred in the application");
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<Response<Object>> handleException(
                        Exception ex) {

                log.error("Unexpected exception", ex);

                return buildErrorResponse(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                "Internal server error");
        }

        private ResponseEntity<Response<Object>> buildErrorResponse(
                        HttpStatus status,
                        String message) {

                Response<Object> response = new Response<>(ResponseStatus.ERROR);

                response.setMessage(message);
                response.setData(null);

                return ResponseEntity
                                .status(status)
                                .body(response);
        }

        @ExceptionHandler(DuplicateIsbnException.class)
        public ResponseEntity<Response<Object>> handleDuplicateIsbn(
                        DuplicateIsbnException ex) {

                log.warn("Duplicate ISBN: {}", ex.getMessage());

                return buildErrorResponse(
                                HttpStatus.CONFLICT,
                                ex.getMessage());
        }

        @ExceptionHandler(BookNotFoundException.class)
        public ResponseEntity<Response<Object>> handleBookNotFound(BookNotFoundException ex) {
                log.warn("Book not found: {}", ex.getMessage());
                return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        }

        @ExceptionHandler(EmptyRequestException.class)
        public ResponseEntity<Response<Object>> handleEmptyRequest(EmptyRequestException ex) {
                log.warn("Empty request: {}", ex.getMessage());
                return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
}