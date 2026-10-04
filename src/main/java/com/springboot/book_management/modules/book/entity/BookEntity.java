package com.springboot.book_management.modules.book.entity;

import java.time.LocalDate;

import com.springboot.book_management.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "books")
@Entity
@Setter
@Getter
public class BookEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column (name = "isbn")
    private String isbn;

    @Column (name = "published_date")
    private LocalDate publishedDate;
}
