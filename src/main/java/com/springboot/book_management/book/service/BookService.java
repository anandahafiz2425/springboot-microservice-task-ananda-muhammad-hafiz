package com.springboot.book_management.book.service;

import com.springboot.book_management.common.response.PageResponse;
import com.springboot.book_management.book.dto.request.CreateBookRequest;
import com.springboot.book_management.book.dto.request.InquiryBookRequest;
import com.springboot.book_management.book.dto.request.PatchBookRequest;
import com.springboot.book_management.book.dto.request.UpdateBookRequest;
import com.springboot.book_management.book.dto.response.BookResponse;

public interface BookService {
    BookResponse createBook(CreateBookRequest request);
    BookResponse findById(Long id);
    BookResponse updateBook(Long id, UpdateBookRequest request);
    BookResponse patchBook(Long id, PatchBookRequest request);
    void deleteBook(Long id);
    PageResponse<BookResponse> inquiry(InquiryBookRequest request);
}
