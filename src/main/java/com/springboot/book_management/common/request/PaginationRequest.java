package com.springboot.book_management.common.request;

import lombok.Setter;
import jakarta.validation.constraints.Min;
import lombok.Getter;

@Setter
@Getter
public class PaginationRequest {
    @Min(value = 1, message = "Page number must be greater than or equal to 1")
    private Integer pageNumber = 1;

    @Min(value = 1, message = "Page size must be greater than or equal to 1")
    private Integer pageSize = 10;
}
