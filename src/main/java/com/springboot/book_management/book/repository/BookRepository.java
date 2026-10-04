package com.springboot.book_management.book.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.book_management.book.entity.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndBookIdNot(String isbn, Long bookId);

    @Query(value = """
            SELECT
                b.book_id,
                b.title,
                b.author,
                b.isbn,
                b.published_date
            FROM books b
            WHERE (:title IS NULL OR :title = '' OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')))
              AND (:author IS NULL OR :author = '' OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%')))
              AND (:isbn IS NULL OR :isbn = '' OR LOWER(b.isbn) LIKE LOWER(CONCAT('%', :isbn, '%')))
              AND (CAST(:publishedDate AS date) IS NULL OR b.published_date = CAST(:publishedDate AS date))
            ORDER BY b.book_id DESC
            LIMIT :limit OFFSET :offset
                """, nativeQuery = true)
    List<Object[]> inquiryBooks(@Param("title") String title, @Param("author") String author,
            @Param("isbn") String isbn, @Param("publishedDate") LocalDate publishedDate, @Param("offset") Integer offset,
            @Param("limit") Integer limit);

    @Query(value = """
            SELECT COUNT(*)
            FROM books b
            WHERE (:title IS NULL OR :title = '' OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')))
              AND (:author IS NULL OR :author = '' OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%')))
              AND (:isbn IS NULL OR :isbn = '' OR LOWER(b.isbn) LIKE LOWER(CONCAT('%', :isbn, '%')))
              AND (CAST(:publishedDate AS date) IS NULL OR b.published_date = CAST(:publishedDate AS date))
                """, nativeQuery = true)
    Long count(@Param("title") String title, @Param("author") String author, @Param("isbn") String isbn,
            @Param("publishedDate") LocalDate publishedDate);
}
