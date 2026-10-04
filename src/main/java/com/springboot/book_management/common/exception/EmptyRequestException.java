package com.springboot.book_management.common.exception;

public class EmptyRequestException extends RuntimeException {
    
    public EmptyRequestException() {
        super("No fields provided for update.");
    }
    
}
