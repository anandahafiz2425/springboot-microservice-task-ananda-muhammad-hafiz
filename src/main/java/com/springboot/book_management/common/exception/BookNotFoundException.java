package com.springboot.book_management.common.exception;

public class BookNotFoundException extends RuntimeException {
    
    public BookNotFoundException(Long id) {
        super("Book with ID " + id + " not found");
    }
    
}
