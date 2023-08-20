package service.impl;

import config.DataSource;
import dao.AuthorDao;
import entity.Author;
import exception.DaoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorDao authorDao;
    @Mock
    private Connection connection;
    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    void testAddAuthor() throws SQLException {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenReturn(connection);
            Author author = new Author("name");
            when(authorDao.save(author, connection)).thenReturn(new Author(1L, "name"));

            authorService.addAuthor(author);

            datasource.verify(DataSource::getConnection);
            verify(authorDao).save(author, connection);
        }
    }

    @Test
    void testAddAuthorWhenSQLExceptionThenThrowDaoException() {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenThrow(new SQLException());
            Author author = new Author("name");

            assertThrows(DaoException.class, () -> authorService.addAuthor(author));
        }
    }

    @Test
    void testGetAuthorById() throws SQLException {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenReturn(connection);
            when(authorDao.findById(1L, connection)).thenReturn(Optional.of(new Author(1L, "name")));

            authorService.getAuthorById(1L);

            datasource.verify(DataSource::getConnection);
            verify(authorDao).findById(1L, connection);
        }
    }

    @Test
    void testGetAuthorByIdWhenSQLExceptionThenThrowDaoException() {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenThrow(new SQLException());

            assertThrows(DaoException.class, () -> authorService.getAuthorById(1L));
        }
    }

    @Test
    void testGetAllAuthors() throws SQLException {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenReturn(connection);
            when(authorDao.findAll(connection)).thenReturn(Collections.emptyList());

            authorService.getAllAuthors();

            datasource.verify(DataSource::getConnection);
            verify(authorDao).findAll(connection);
        }
    }

    @Test
    void testGetAllAuthorsWhenSQLExceptionThenThrowDaoException() {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenThrow(new SQLException());

            assertThrows(DaoException.class, () -> authorService.getAllAuthors());
        }
    }

    @Test
    void testUpdateAuthor() throws SQLException {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenReturn(connection);
            Author author = new Author("name");
            when(authorDao.update(1L, author, connection))
                    .thenReturn(new Author(1L, "name"));

            authorService.updateAuthor(1L, author);

            datasource.verify(DataSource::getConnection);
            verify(authorDao).update(1L, author, connection);
        }
    }

    @Test
    void testUpdateAuthorWhenSQLExceptionThenThrowDaoException() {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenThrow(new SQLException());
            Author author = new Author("name");

            assertThrows(DaoException.class, () -> authorService.updateAuthor(1L, author));
        }
    }

    @Test
    void testDeleteAuthor() throws SQLException {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenReturn(connection);
            doNothing().when(authorDao).delete(1L, connection);
            authorService.deleteAuthor(1L);

            datasource.verify(DataSource::getConnection);
            verify(authorDao).delete(1L, connection);
        }
    }

    @Test
    void testDeleteAuthorWhenSQLExceptionThenThrowDaoException() {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenThrow(new SQLException());

            assertThrows(DaoException.class, () -> authorService.deleteAuthor(1L));
        }
    }

}