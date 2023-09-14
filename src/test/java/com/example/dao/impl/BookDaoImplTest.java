package com.example.dao.impl;

import com.example.dao.impl.AuthorDaoImpl;
import com.example.dao.impl.BookDaoImpl;
import com.example.dao.impl.PublishingHouseDaoImpl;
import com.example.config.AbstractPostgresContainer;
import com.example.dao.AuthorDao;
import com.example.dao.BookDao;
import com.example.dao.PublishingHouseDao;
import com.example.entity.Author;
import com.example.entity.Book;
import com.example.entity.PublishingHouse;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookDaoImplTest extends AbstractPostgresContainer {

    private static final BookDao bookDao = BookDaoImpl.getInstance();
    private static final AuthorDao authorDao = AuthorDaoImpl.getInstance();
    private static final PublishingHouseDao publishingHouseDao = PublishingHouseDaoImpl.getInstance();

    @Test
    void testSave() {
        PublishingHouse publishingHouse = publishingHouseDao.save(new PublishingHouse("Crown"));
        Author author = authorDao.save(new Author("John"));
        Book bookForSave = new Book("Java", publishingHouse, List.of(author));
        Book savedBook = bookDao.save(bookForSave);

        assertAll(
                () -> assertNotNull(savedBook.getId(), "Saved book id must not be null"),
                () -> assertEquals(publishingHouse, savedBook.getPublishingHouse()),
                () -> assertEquals(1, savedBook.getAuthors().size(),
                        "The savedBook.getAuthors().size() must be equal 1")
        );

        bookDao.delete(savedBook.getId());
        authorDao.delete(author.getId());
        publishingHouseDao.delete(publishingHouse.getId());
    }

    @Test
    void testFindAllAuthorsByBookId() {
        PublishingHouse publishingHouse = publishingHouseDao.save(new PublishingHouse("West"));
        Author author1 = authorDao.save(new Author("John"));
        Author author2 = authorDao.save(new Author("Jack"));
        List<Author> authors = List.of(author1, author2);
        Book bookForSave = new Book("Clean code", publishingHouse, authors);
        Book savedBook = bookDao.save(bookForSave);
        List<Author> savedAuthors = bookDao.findAllAuthorsByBookId(savedBook.getId());

        assertEquals(savedAuthors, savedBook.getAuthors());

        bookDao.delete(savedBook.getId());
        authorDao.delete(author1.getId());
        authorDao.delete(author2.getId());
        publishingHouseDao.delete(publishingHouse.getId());
    }

    @Test
    void testFindAll() {
        PublishingHouse publishingHouse = publishingHouseDao.save(new PublishingHouse("Crown"));
        Book bookForSave = new Book("Algorithm", publishingHouse);
        Book savedBook = bookDao.save(bookForSave);

        assertEquals(1, bookDao.findAll().size(),
                "The findAll method must return a list with size = 1");

        bookDao.delete(savedBook.getId());
        publishingHouseDao.delete(publishingHouse.getId());
    }

    @Test
    void testFindById() {
        PublishingHouse publishingHouse = publishingHouseDao.save(new PublishingHouse("Crown"));
        Book bookForSave = new Book("Algorithm", publishingHouse);
        Book savedBook = bookDao.save(bookForSave);

        assertAll(
                () -> assertNotEquals(Optional.empty(),
                        bookDao.findById(savedBook.getId()),
                        "The findById method for savedBook must return a non-empty Optional"),
                () -> assertEquals(Optional.empty(),
                        bookDao.findById(Long.MAX_VALUE),
                        "The findById method for Long.MAX_VALUE must return empty Optional")
        );

        bookDao.delete(savedBook.getId());
        publishingHouseDao.delete(publishingHouse.getId());
    }

    @Test
    void testUpdate() {
        PublishingHouse publishingHouse1 = publishingHouseDao.save(new PublishingHouse("Crown"));
        PublishingHouse publishingHouse2 = publishingHouseDao.save(new PublishingHouse("Mega"));
        Book bookForSave = new Book("Clean code", publishingHouse1);
        Book bookForUpdate = new Book("Name", publishingHouse2);
        Book savedBook = bookDao.save(bookForSave);
        Book updatedBook = bookDao.update(savedBook.getId(), bookForUpdate);

        assertAll(
                () -> assertEquals("Name",
                        bookDao.findById(savedBook.getId()).orElse(new Book()).getName()),
                () -> assertEquals("Mega",
                        bookDao.findById(savedBook.getId())
                                .orElse(new Book()).getPublishingHouse().getName())
        );

        bookDao.delete(savedBook.getId());
        bookDao.delete(updatedBook.getId());
        publishingHouseDao.delete(publishingHouse1.getId());
        publishingHouseDao.delete(publishingHouse2.getId());
    }

    @Test
    void testDelete() {
        PublishingHouse publishingHouse = publishingHouseDao.save(new PublishingHouse("Crown"));
        Book bookForSave = new Book("Clean code", publishingHouse);
        Book savedBook = bookDao.save(bookForSave);

        assertEquals(1, bookDao.findAll().size(),
                "The findAll method must return a list with size = 1");

        bookDao.delete(savedBook.getId());

        assertEquals(0, bookDao.findAll().size(),
                "The findAll method must return a list with size = 0");

        publishingHouseDao.delete(publishingHouse.getId());
    }

}