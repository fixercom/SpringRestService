package com.example.repository;

import com.example.AbstractIntegrationTest;
import com.example.config.JpaConfig;
import com.example.entity.Author;
import com.example.entity.Book;
import com.example.entity.PublishingHouse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = {JpaConfig.class,
        AuthorRepository.class,
        PublishingHouseRepository.class,
        BookRepository.class})
class AuthorRepositoryTest extends AbstractIntegrationTest {

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
        Author author = new Author();
        author.setName("Pushkin");
        author = authorRepository.save(author);
        PublishingHouse publishingHouse = new PublishingHouse();
        publishingHouse.setName("First house");
        publishingHouse = publishingHouseRepository.save(publishingHouse);
        Book book1 = new Book();
        Book book2 = new Book();
        book1.setName("book1");
        book2.setName("book2");
        book1.setPublishingHouse(publishingHouse);
        book2.setPublishingHouse(publishingHouse);
        book1.setAuthors(Set.of(author));
        book2.setAuthors(Set.of(author));
        book1 = bookRepository.save(book1);
        book2 = bookRepository.save(book2);

        Author savedAuthor = authorRepository.findByIdWithBooks(author.getId()).orElseThrow();

        Set<String> bookNames = savedAuthor.getBooks().stream()
                .map(Book::getName)
                .collect(Collectors.toSet());
        assertEquals(2, savedAuthor.getBooks().size());
        assertTrue(bookNames.contains("book1"));
        assertTrue(bookNames.contains("book2"));

        bookRepository.delete(book1);
        bookRepository.delete(book2);
        publishingHouseRepository.delete(publishingHouse);
        authorRepository.delete(author);
    }

}