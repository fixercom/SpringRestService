package com.example.repository;

import com.example.AbstractIntegrationTest;
import com.example.config.JpaConfig;
import com.example.entity.Author;
import com.example.entity.Book;
import com.example.entity.PublishingHouse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = {JpaConfig.class,
        BookRepository.class,
        AuthorRepository.class,
        PublishingHouseRepository.class})
class BookRepositoryTest extends AbstractIntegrationTest {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublishingHouseRepository publishingHouseRepository;

    @Autowired
    public BookRepositoryTest(BookRepository bookRepository,
                              AuthorRepository authorRepository,
                              PublishingHouseRepository publishingHouseRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publishingHouseRepository = publishingHouseRepository;
    }

    @Test
    @Sql("/schema.sql")
    void findByIdWithAuthors() {
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
        book = bookRepository.save(book);

        Book savedBook = bookRepository.findByIdWithAuthors(book.getId()).orElseThrow();
        Set<String> authorNames = savedBook.getAuthors().stream()
                .map(Author::getName)
                .collect(Collectors.toSet());

        assertEquals(2, savedBook.getAuthors().size());
        assertTrue(authorNames.contains("Pushkin"));
        assertTrue(authorNames.contains("Dostoevsky"));

        bookRepository.delete(book);
        publishingHouseRepository.delete(publishingHouse);
        authorRepository.delete(author1);
        authorRepository.delete(author2);
    }
}