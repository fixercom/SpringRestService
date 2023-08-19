package dao.impl;

import config.TestDataSource;
import dao.AuthorDao;
import dao.BookDao;
import dao.PublishingHouseDao;
import entity.Author;
import entity.Book;
import entity.PublishingHouse;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookDaoImplTest {

    private static final BookDao bookDao = BookDaoImpl.getInstance();
    private static final AuthorDao authorDao = AuthorDaoImpl.getInstance();
    private static final PublishingHouseDao publishingHouseDao = PublishingHouseDaoImpl.getInstance();

    @Test
    void testSave() throws SQLException {
        try (Connection connection = TestDataSource.getConnection()) {
            PublishingHouse publishingHouse = publishingHouseDao.save(new PublishingHouse("Crown"), connection);
            Author author = authorDao.save(new Author("John"), connection);
            Book bookForSave = new Book("Java", publishingHouse, List.of(author));
            Book savedBook = bookDao.save(bookForSave, connection);

            assertAll(
                    () -> assertNotNull(savedBook.getId(), "Saved book id must not be null"),
                    () -> assertEquals(publishingHouse, savedBook.getPublishingHouse()),
                    () -> assertEquals(1, savedBook.getAuthors().size(),
                            "The savedBook.getAuthors().size() must be equal 1")
            );

            bookDao.delete(savedBook.getId(), connection);
            authorDao.delete(author.getId(), connection);
            publishingHouseDao.delete(publishingHouse.getId(), connection);
        }
    }

    @Test
    void testFindAllAuthorsByBookId() throws SQLException {
        try (Connection connection = TestDataSource.getConnection()) {
            PublishingHouse publishingHouse = publishingHouseDao.save(new PublishingHouse("West"), connection);
            Author author1 = authorDao.save(new Author("John"), connection);
            Author author2 = authorDao.save(new Author("Jack"), connection);
            List<Author> authors = List.of(author1, author2);
            Book bookForSave = new Book("Clean code", publishingHouse, authors);
            Book savedBook = bookDao.save(bookForSave, connection);
            List<Author> savedAuthors = bookDao.findAllAuthorsByBookId(savedBook.getId(), connection);

            assertEquals(savedAuthors, savedBook.getAuthors());

            bookDao.delete(savedBook.getId(), connection);
            authorDao.delete(author1.getId(), connection);
            authorDao.delete(author2.getId(), connection);
            publishingHouseDao.delete(publishingHouse.getId(), connection);
        }
    }

    @Test
    void testFindAll() throws SQLException {
        try (Connection connection = TestDataSource.getConnection()) {
            PublishingHouse publishingHouse = publishingHouseDao.save(new PublishingHouse("Crown"), connection);
            Book bookForSave = new Book("Algorithm", publishingHouse);
            Book savedBook = bookDao.save(bookForSave, connection);

            assertEquals(1, bookDao.findAll(connection).size(),
                    "The findAll method must return a list with size = 1");

            bookDao.delete(savedBook.getId(), connection);
            publishingHouseDao.delete(publishingHouse.getId(), connection);
        }
    }

    @Test
    void testFindById() throws SQLException {
        try (Connection connection = TestDataSource.getConnection()) {
            PublishingHouse publishingHouse = publishingHouseDao.save(new PublishingHouse("Crown"), connection);
            Book bookForSave = new Book("Algorithm", publishingHouse);
            Book savedBook = bookDao.save(bookForSave, connection);

            assertAll(
                    () -> assertNotEquals(Optional.empty(),
                            bookDao.findById(savedBook.getId(), connection),
                            "The findById method for savedBook must return a non-empty Optional"),
                    () -> assertEquals(Optional.empty(),
                            bookDao.findById(Long.MAX_VALUE, connection),
                            "The findById method for Long.MAX_VALUE must return empty Optional")
            );

            bookDao.delete(savedBook.getId(), connection);
            publishingHouseDao.delete(publishingHouse.getId(), connection);
        }
    }

    @Test
    void testUpdate() throws SQLException {
        try (Connection connection = TestDataSource.getConnection()) {
            PublishingHouse publishingHouse1 = publishingHouseDao.save(new PublishingHouse("Crown"), connection);
            PublishingHouse publishingHouse2 = publishingHouseDao.save(new PublishingHouse("Mega"), connection);
            Book bookForSave = new Book("Clean code", publishingHouse1);
            Book bookForUpdate = new Book("Name", publishingHouse2);
            Book savedBook = bookDao.save(bookForSave, connection);
            Book updatedBook = bookDao.update(savedBook.getId(), bookForUpdate, connection);

            assertAll(
                    () -> assertEquals("Name",
                            bookDao.findById(savedBook.getId(), connection).orElse(new Book()).getName()),
                    () -> assertEquals("Mega",
                            bookDao.findById(savedBook.getId(), connection)
                                    .orElse(new Book()).getPublishingHouse().getName())
            );

            bookDao.delete(savedBook.getId(), connection);
            bookDao.delete(updatedBook.getId(), connection);
            publishingHouseDao.delete(publishingHouse1.getId(), connection);
            publishingHouseDao.delete(publishingHouse2.getId(), connection);
        }
    }

    @Test
    void testDelete() throws SQLException {
        try (Connection connection = TestDataSource.getConnection()) {
            PublishingHouse publishingHouse = publishingHouseDao.save(new PublishingHouse("Crown"), connection);
            Book bookForSave = new Book("Clean code", publishingHouse);
            Book savedBook = bookDao.save(bookForSave, connection);

            assertEquals(1, bookDao.findAll(connection).size(),
                    "The findAll method must return a list with size = 1");

            bookDao.delete(savedBook.getId(), connection);

            assertEquals(0, bookDao.findAll(connection).size(),
                    "The findAll method must return a list with size = 0");

            publishingHouseDao.delete(publishingHouse.getId(), connection);
        }
    }

}