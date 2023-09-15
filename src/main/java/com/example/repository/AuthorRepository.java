package com.example.repository;

import com.example.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository("authorRepository")
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a LEFT JOIN FETCH a.books WHERE a.id = ?1")
    Optional<Author> findByIdWithBooks(Long id);

    Set<Author> findByIdIn(Set<Long> authorIds);

}
