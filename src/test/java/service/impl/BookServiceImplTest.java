package service.impl;

import config.DataSource;
import dao.BookDao;
import entity.Author;
import entity.Book;
import entity.PublishingHouse;
import exception.DaoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookDao bookDao;
    @Mock
    private Connection connection;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void testAddBook() throws SQLException {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenReturn(connection);
            PublishingHouse publishingHouse = new PublishingHouse(1L, "Crown");
            Book book = new Book(10L, "bookName", publishingHouse);
            doNothing().when(connection).setAutoCommit(false);
            when(bookDao.save(book, connection)).thenReturn(book);
            when(bookDao.findAllAuthorsByBookId(10L, connection))
                    .thenReturn(List.of(new Author(33L, "authorName")));

            bookService.addBook(book);

            datasource.verify(DataSource::getConnection);
            InOrder inOrder = inOrder(connection, bookDao);
            inOrder.verify(connection).setAutoCommit(false);
            inOrder.verify(bookDao).save(book, connection);
            inOrder.verify(bookDao).findAllAuthorsByBookId(10L, connection);
            inOrder.verify(connection).commit();
        }
    }

    @Test
    void testAddBookWhenSQLExceptionThenThrowDaoException() {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenThrow(new SQLException());
            Book book = new Book();

            assertThrows(DaoException.class, () -> bookService.addBook(book));
        }
    }

    @Test
    void testGetBookById() throws SQLException {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenReturn(connection);
            PublishingHouse publishingHouse = new PublishingHouse(1L, "Crown");
            Book book = new Book(10L, "bookName", publishingHouse);
            when(bookDao.findById(10L, connection)).thenReturn(Optional.of(book));
            when(bookDao.findAllAuthorsByBookId(10L, connection))
                    .thenReturn(List.of(new Author("authorName")));

            bookService.getBookById(10L);

            datasource.verify(DataSource::getConnection);
            InOrder inOrder = inOrder(bookDao);
            inOrder.verify(bookDao).findById(10L, connection);
            inOrder.verify(bookDao).findAllAuthorsByBookId(10L, connection);
        }
    }

    @Test
    void testGetBookByIdWhenSQLExceptionThenThrowDaoException() {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenThrow(new SQLException());

            assertThrows(DaoException.class, () -> bookService.getBookById(1L));
        }
    }

    @Test
    void testGetAllBooks() throws SQLException {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenReturn(connection);
            when(bookDao.findAll(connection)).thenReturn(Collections.emptyList());

            bookService.getAllBooks();

            datasource.verify(DataSource::getConnection);
            verify(bookDao).findAll(connection);
        }
    }

    @Test
    void testGetAllBooksWhenSQLExceptionThenThrowDaoException() {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenThrow(new SQLException());

            assertThrows(DaoException.class, () -> bookService.getAllBooks());
        }
    }

    @Test
    void testUpdateBook() throws SQLException {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenReturn(connection);
            PublishingHouse publishingHouse = new PublishingHouse(1L, "Crown");
            Book book = new Book(10L, "bookName", publishingHouse);
            when(bookDao.update(10L, book, connection)).thenReturn(book);

            bookService.updateBook(10L, book);

            datasource.verify(DataSource::getConnection);
            verify(bookDao).update(10L, book, connection);
        }
    }

    @Test
    void testUpdateBookWhenSQLExceptionThenThrowDaoException() {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenThrow(new SQLException());
            PublishingHouse publishingHouse = new PublishingHouse(1L, "Crown");
            Book book = new Book(10L, "bookName", publishingHouse);

            assertThrows(DaoException.class, () -> bookService.updateBook(10L,book));
        }
    }

    @Test
    void deleteBook() throws SQLException {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenReturn(connection);
            doNothing().when(bookDao).delete(1L, connection);
            bookService.deleteBook(1L);

            datasource.verify(DataSource::getConnection);
            verify(bookDao).delete(1L, connection);
        }
    }
}