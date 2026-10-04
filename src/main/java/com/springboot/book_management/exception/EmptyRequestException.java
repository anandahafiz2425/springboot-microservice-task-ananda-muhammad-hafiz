package com.springboot.book_management.exception;

public class EmptyRequestException extends RuntimeException {
    
    public EmptyRequestException() {
        super("No fields provided for update.");
    }
    
}
