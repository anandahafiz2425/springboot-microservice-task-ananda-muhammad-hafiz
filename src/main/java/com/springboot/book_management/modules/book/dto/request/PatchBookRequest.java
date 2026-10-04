package com.springboot.book_management.modules.book.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatchBookRequest {
    private String title;

    private String author;

    private String isbn;

    @PastOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate publishedDate;

    public boolean isEmpty() {
        return (title == null || title.isBlank()) &&
                (author == null || author.isBlank()) &&
                (isbn == null || isbn.isBlank()) &&
                (publishedDate == null);
    }
}
