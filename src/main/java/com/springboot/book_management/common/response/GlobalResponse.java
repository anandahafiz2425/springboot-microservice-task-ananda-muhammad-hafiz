package com.springboot.book_management.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class GlobalResponse {

    public <T> ResponseEntity<Response<T>> success(String message, T data) {

        Response<T> response = new Response<>(
                ResponseStatus.SUCCESS,
                message,
                data);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    public ResponseEntity<Response<Void>> error(
            HttpStatus status,
            String message) {

        Response<Void> response = new Response<>(
                ResponseStatus.ERROR,
                message,
                null);

        return ResponseEntity
                .status(status)
                .body(response);
    }
}