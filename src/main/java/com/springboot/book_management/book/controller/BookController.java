package com.springboot.book_management.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.book_management.common.response.Response;
import com.springboot.book_management.common.response.GlobalResponse;
import com.springboot.book_management.common.response.PageResponse;
import com.springboot.book_management.book.dto.request.CreateBookRequest;
import com.springboot.book_management.book.dto.request.InquiryBookRequest;
import com.springboot.book_management.book.dto.request.PatchBookRequest;
import com.springboot.book_management.book.dto.request.UpdateBookRequest;
import com.springboot.book_management.book.dto.response.BookResponse;
import com.springboot.book_management.book.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    GlobalResponse responseUtil;

    @Autowired
    BookService bookService;

    @PostMapping
    public ResponseEntity<Response<BookResponse>> createBook(@Valid @RequestBody CreateBookRequest request) {
        BookResponse response = bookService.createBook(request);
        return responseUtil.success("Create book successfully", response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<BookResponse>> findById(@PathVariable Long id) {
        BookResponse data = bookService.findById(id);
        return responseUtil.success("Book found successfully", data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<BookResponse>> updateBook(@PathVariable Long id,
            @Valid @RequestBody UpdateBookRequest request) {
        BookResponse data = bookService.updateBook(id, request);
        return responseUtil.success("Book updated successfully", data);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response<BookResponse>> patchBook(@PathVariable Long id,
            @RequestBody PatchBookRequest request) {
        BookResponse data = bookService.patchBook(id, request);
        return responseUtil.success("Book patched successfully", data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return responseUtil.success("Book deleted successfully", null);
    }

    @PostMapping("/inquiry")
    public ResponseEntity<Response<PageResponse<BookResponse>>> inquiry(@RequestBody InquiryBookRequest request) {
        return responseUtil.success("Inquiry book successfully",
                bookService.inquiry(request));
    }
}
