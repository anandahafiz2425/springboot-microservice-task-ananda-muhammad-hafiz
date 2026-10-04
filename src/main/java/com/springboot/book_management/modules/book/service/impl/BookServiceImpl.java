package com.springboot.book_management.modules.book.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.springboot.book_management.common.exception.BookNotFoundException;
import com.springboot.book_management.common.exception.DuplicateIsbnException;
import com.springboot.book_management.common.exception.EmptyRequestException;
import com.springboot.book_management.common.response.PageResponse;
import com.springboot.book_management.common.service.BaseService;
import com.springboot.book_management.modules.book.dto.request.CreateBookRequest;
import com.springboot.book_management.modules.book.dto.request.InquiryBookRequest;
import com.springboot.book_management.modules.book.dto.request.PatchBookRequest;
import com.springboot.book_management.modules.book.dto.request.UpdateBookRequest;
import com.springboot.book_management.modules.book.dto.response.BookResponse;
import com.springboot.book_management.modules.book.entity.BookEntity;
import com.springboot.book_management.modules.book.mapper.BookMapper;
import com.springboot.book_management.modules.book.repository.BookRepository;
import com.springboot.book_management.modules.book.service.BookService;

import jakarta.transaction.Transactional;

@Service
public class BookServiceImpl extends BaseService implements BookService {
    @Autowired
    BookRepository bookRepository;

    @Transactional
    public BookResponse createBook(CreateBookRequest request) {
        String isbn = request.getIsbn().replaceAll("[\\s-]", "").toUpperCase();

        if (bookRepository.existsByIsbn(isbn)) {
            throw new DuplicateIsbnException(isbn);
        }

        BookEntity saved = bookRepository.save(BookMapper.toEntity(request));
        return BookMapper.toResponse(saved);
    }

    private BookEntity getBookOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public BookResponse findById(Long id) {
        return BookMapper.toResponse(getBookOrThrow(id));
    }

    @Transactional
    public BookResponse updateBook(Long id, UpdateBookRequest request) {
        BookEntity book = getBookOrThrow(id);

        String newIsbn = request.getIsbn().replaceAll("[\\s-]", "").toUpperCase();
        if (!book.getIsbn().equals(newIsbn) && bookRepository.existsByIsbn(newIsbn)) {
            throw new DuplicateIsbnException(newIsbn);
        }

        BookMapper.updateEntity(book, request);
        return BookMapper.toResponse(bookRepository.saveAndFlush(book));
    }

    @Transactional
    public BookResponse patchBook(Long id, PatchBookRequest request) {
        if (request.isEmpty()) {
            throw new EmptyRequestException();
        }
        BookEntity book = getBookOrThrow(id);

        if (request.getIsbn() != null) {
            String newIsbn = request.getIsbn().replaceAll("[\\s-]", "").toUpperCase();
            if (!book.getIsbn().equals(newIsbn) && bookRepository.existsByIsbnAndBookIdNot(newIsbn, id)) {
                throw new DuplicateIsbnException(newIsbn);
            }
        }

        BookMapper.patchEntity(book, request);
        return BookMapper.toResponse(bookRepository.saveAndFlush(book));
    }

    @Transactional
    public void deleteBook(Long id) {
        BookEntity book = getBookOrThrow(id);
        bookRepository.delete(book);
    }

    public PageResponse<BookResponse> inquiry(InquiryBookRequest request) {

        List<BookResponse> response = new ArrayList<>();
        Long count = 0L;
        Integer offset = (request.getPageNumber() - 1);

        List<Object[]> result = bookRepository.inquiryBooks(
                request.getTitle(),
                request.getAuthor(),
                request.getIsbn(),
                request.getPublishedDate(),
                offset * request.getPageSize(),
                request.getPageSize());

        for (Object[] row : result) {
            BookMapper.getEntity(row, response);
        }

        count = bookRepository.count(request.getTitle(), request.getAuthor(), request.getIsbn(),
                request.getPublishedDate());

        final Page<BookResponse> page = new PageImpl<>(response, getPageable(request), count);

        return pageResponse(page, request);
    }
}
