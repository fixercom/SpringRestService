package com.example.repository;

import com.example.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b " +
            "LEFT JOIN FETCH b.authors " +
            "LEFT JOIN FETCH b.publishingHouse " +
            "WHERE b.id = ?1")
    Optional<Book> findByIdWithAuthors(Long bookId);
}
