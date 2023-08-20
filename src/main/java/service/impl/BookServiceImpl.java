package service.impl;

import config.DataSource;
import dao.BookDao;
import dao.impl.BookDaoImpl;
import entity.Author;
import entity.Book;
import exception.DaoException;
import service.BookService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookServiceImpl implements BookService {

    private static final BookServiceImpl INSTANCE = new BookServiceImpl();
    private BookDao bookDao = BookDaoImpl.getInstance();

    private BookServiceImpl() {
    }

    public static BookServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Book addBook(Book book) {
        try (Connection connection = DataSource.getConnection()) {
            connection.setAutoCommit(false);
            book = bookDao.save(book, connection);
            List<Author> authors = bookDao.findAllAuthorsByBookId(book.getId(), connection);
            book.setAuthors(authors);
            connection.commit();
            return book;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Book getBookById(Long id) {
        try (Connection connection = DataSource.getConnection()) {
            Book book = bookDao.findById(id, connection).orElseThrow();
            List<Author> authors = bookDao.findAllAuthorsByBookId(book.getId(), connection);
            book.setAuthors(authors);
            return book;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        try (Connection connection = DataSource.getConnection()) {
            return bookDao.findAll(connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Book updateBook(Long id, Book book) {
        try (Connection connection = DataSource.getConnection()) {
            return bookDao.update(id, book, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteBook(Long id) {
        try (Connection connection = DataSource.getConnection()) {
            bookDao.delete(id, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
