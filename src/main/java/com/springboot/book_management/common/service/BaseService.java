package com.springboot.book_management.common.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.springboot.book_management.common.request.PaginationRequest;
import com.springboot.book_management.common.response.PageResponse;

public abstract class BaseService {

    protected Pageable getPageable(PaginationRequest request) {
        return PageRequest.of(
                request.getPageNumber() - 1,
                request.getPageSize());
    }

    protected <T> PageResponse<T> pageResponse(
            Page<T> page,
            PaginationRequest request) {
        List<T> data = page.getContent();

        return new PageResponse<>(
                request.getPageNumber(),
                request.getPageSize(),
                data.size(),
                page.getTotalElements(),
                page.getTotalPages(),
                data);
    }
}
