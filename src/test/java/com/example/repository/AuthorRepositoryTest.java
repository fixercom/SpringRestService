package com.example.repository;

import com.example.config.JpaConfig;
import com.example.entity.Author;
import com.example.entity.Book;
import com.example.entity.PublishingHouse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = {JpaConfig.class,
        AuthorRepository.class,
        PublishingHouseRepository.class,
        BookRepository.class})
@TestPropertySource(locations = "classpath:test-datasource.properties")
@Testcontainers
class AuthorRepositoryTest {

    @Container
    private final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14-alpine");
    private final AuthorRepository authorRepository;
    private final PublishingHouseRepository publishingHouseRepository;
    private final BookRepository bookRepository;

    @Autowired
    public AuthorRepositoryTest(AuthorRepository authorRepository,
                                PublishingHouseRepository publishingHouseRepository,
                                BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.publishingHouseRepository = publishingHouseRepository;
        this.bookRepository = bookRepository;
    }

    @Test
    void findByIdWithBooks() {
        Author author1 = new Author();
        author1.setName("Pushkin");
        author1 = authorRepository.save(author1);
        Author author2 = new Author();
        author2.setName("Dostoevsky");
        author2 = authorRepository.save(author2);
        PublishingHouse publishingHouse = new PublishingHouse();
        publishingHouse.setName("First house");
        publishingHouse = publishingHouseRepository.save(publishingHouse);
        Book book = new Book();
        book.setName("book");
        book.setPublishingHouse(publishingHouse);
        book.setAuthors(Set.of(author1, author2));
        bookRepository.save(book);

        Book savedBook = bookRepository.findByIdWithAuthors(1L).orElseThrow();
        Set<String> authorNames = savedBook.getAuthors().stream()
                .map(Author::getName)
                .collect(Collectors.toSet());

        assertEquals(2, savedBook.getAuthors().size());
        assertTrue(authorNames.contains("Pushkin"));
        assertTrue(authorNames.contains("Dostoevsky"));
    }

}