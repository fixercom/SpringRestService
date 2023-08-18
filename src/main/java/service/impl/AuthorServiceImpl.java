package service.impl;

import config.DataSource;
import dao.AuthorDao;
import dao.impl.AuthorDaoImpl;
import entity.Author;
import exception.DaoException;
import service.AuthorService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    private static final AuthorServiceImpl INSTANCE = new AuthorServiceImpl();

    private AuthorServiceImpl() {
    }

    public static AuthorServiceImpl getInstance() {
        return INSTANCE;
    }

    private final AuthorDao authorDao = AuthorDaoImpl.getInstance();

    @Override
    public Author addAuthor(Author author) {
        try (Connection connection = DataSource.getConnection()) {
            return authorDao.save(author, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Author getAuthorById(Long id) {
        try (Connection connection = DataSource.getConnection()) {
            return authorDao.findById(id, connection).orElseThrow();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        try (Connection connection = DataSource.getConnection()) {
            return authorDao.findAll(connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Author updateAuthor(Long id, Author author) {
        try (Connection connection = DataSource.getConnection()) {
            return authorDao.update(id, author, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteAuthor(Long id) {
        try (Connection connection = DataSource.getConnection()) {
            authorDao.delete(id, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
