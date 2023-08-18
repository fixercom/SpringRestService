package dao.impl;

import config.TestDataSource;
import dao.AuthorDao;
import entity.Author;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDaoImplTest {

    private static final AuthorDao authorDao = AuthorDaoImpl.getInstance();

    @Test
    void testSave() throws SQLException {
        try (Connection connection = TestDataSource.getConnection()) {
            String authorName = "Ivan";
            Author author = new Author(authorName);
            Author savedAuthor = authorDao.save(author, connection);

            assertAll(
                    () -> assertNotNull(savedAuthor.getId(), "Saved author id must not be null"),
                    () -> assertEquals(1, authorDao.findAll(connection).size(),
                            "The findAll method must return a list with size = 1")
            );

            authorDao.delete(savedAuthor.getId(), connection);
        }
    }

    @Test
    void testFindById() throws SQLException {
        try (Connection connection = TestDataSource.getConnection()) {
            String authorName = "Andrey";
            Author author = new Author(authorName);
            Author savedAuthor = authorDao.save(author, connection);

            assertAll(
                    () -> assertNotEquals(Optional.empty(),
                            authorDao.findById(savedAuthor.getId(), connection),
                            "The findById method for savedAuthor must return a non-empty Optional"),
                    () -> assertEquals(Optional.empty(),
                            authorDao.findById(Long.MAX_VALUE, connection),
                            "The findById method for Long.MAX_VALUE must return empty Optional")
            );

            authorDao.delete(savedAuthor.getId(), connection);
        }
    }

    @Test
    void testFindAll() throws SQLException {
        try (Connection connection = TestDataSource.getConnection()) {
            Author author = new Author("author1");
            Author savedAuthor = authorDao.save(author, connection);

            assertAll(
                    () -> assertEquals(1, authorDao.findAll(connection).size(),
                            "The findAll method must return a list with size = 1")
            );

            authorDao.delete(savedAuthor.getId(), connection);
        }
    }

    @Test
    void testUpdate() throws SQLException {
        try (Connection connection = TestDataSource.getConnection()) {
            Author author = new Author("Alexey");
            Author authorForUpdate = new Author("Ivan");

            Author savedAuthor = authorDao.save(author, connection);
            authorDao.update(savedAuthor.getId(), authorForUpdate, connection);

            assertAll(
                    () -> assertEquals(1, authorDao.findAll(connection).size(),
                            "The findAll method must return a list with size = 1"),
                    () -> assertEquals("Ivan",
                            authorDao.findById(savedAuthor.getId(), connection).get().getName(),
                            "New name for saved author must be equal 'Ivan'")
            );

            authorDao.delete(savedAuthor.getId(), connection);
        }
    }

    @Test
    void testDelete() throws SQLException {
        try (Connection connection = TestDataSource.getConnection()) {
            Author savedAuthor = authorDao.save(new Author("Robert"), connection);

            assertEquals(1, authorDao.findAll(connection).size(),
                    "The findAll method must return a list with size = 1");

            authorDao.delete(savedAuthor.getId(), connection);

            assertEquals(0, authorDao.findAll(connection).size(),
                    "The findAll method must return a list with size = 0");
        }
    }
}