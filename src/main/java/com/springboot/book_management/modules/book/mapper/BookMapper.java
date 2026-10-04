package com.springboot.book_management.modules.book.mapper;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.springboot.book_management.modules.book.dto.request.CreateBookRequest;
import com.springboot.book_management.modules.book.dto.request.PatchBookRequest;
import com.springboot.book_management.modules.book.dto.request.UpdateBookRequest;
import com.springboot.book_management.modules.book.dto.response.BookResponse;
import com.springboot.book_management.modules.book.entity.BookEntity;

public class BookMapper {

    private BookMapper() {
    }

    public static BookEntity toEntity(CreateBookRequest request) {
        BookEntity book = new BookEntity();
        book.setTitle(request.getTitle().trim());
        book.setAuthor(request.getAuthor().trim());
        book.setIsbn(request.getIsbn().replaceAll("[\\s-]", "").toUpperCase());
        book.setPublishedDate(request.getPublishedDate());
        book.setCreatedAt(new Date());
        return book;
    }

    public static BookResponse toResponse(BookEntity book) {
        return new BookResponse(
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPublishedDate());
    }

    public static void updateEntity(BookEntity book, UpdateBookRequest request) {
        book.setTitle(request.getTitle().trim());
        book.setAuthor(request.getAuthor().trim());
        book.setIsbn(request.getIsbn().replaceAll("[\\s-]", "").toUpperCase());
        book.setPublishedDate(request.getPublishedDate());
        book.setUpdatedAt(new Date());
    }

    public static void patchEntity(BookEntity book, PatchBookRequest request) {
        if (request.getTitle() != null) {
            book.setTitle(request.getTitle().trim());
        }
        if (request.getAuthor() != null) {
            book.setAuthor(request.getAuthor().trim());
        }
        if (request.getIsbn() != null) {
            book.setIsbn(request.getIsbn().replaceAll("[\\s-]", "").toUpperCase());
        }
        if (request.getPublishedDate() != null) {
            book.setPublishedDate(request.getPublishedDate());
        }
        book.setUpdatedAt(new Date());
    }

    public static void getEntity(Object[] row, List<BookResponse> response) {
        BookResponse bookResponse = new BookResponse();
        bookResponse.setBookId(((Long) row[0]).longValue());
        bookResponse.setTitle((String) row[1]);
        bookResponse.setAuthor((String) row[2]);
        bookResponse.setIsbn((String) row[3]);
        bookResponse.setPublishedDate((LocalDate) row[4]);
        response.add(bookResponse);
    }
}
