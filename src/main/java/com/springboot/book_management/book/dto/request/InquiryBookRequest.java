package com.springboot.book_management.book.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.book_management.common.request.PaginationRequest;

import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter 
public class InquiryBookRequest extends PaginationRequest {
    private String title;
    private String author;
    private String isbn;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate publishedDate;
}
