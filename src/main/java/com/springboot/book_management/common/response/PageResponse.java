package com.springboot.book_management.common.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageResponse<T> {

    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalDataInPage;
    private Long totalData;
    private Integer totalPages;
    private List<T> data;

    public PageResponse(
            Integer pageNumber,
            Integer pageSize,
            Integer totalDataInPage,
            Long totalData,
            Integer totalPages,
            List<T> data) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalDataInPage = totalDataInPage;
        this.totalData = totalData;
        this.totalPages = totalPages;
        this.data = data;
    }
}
