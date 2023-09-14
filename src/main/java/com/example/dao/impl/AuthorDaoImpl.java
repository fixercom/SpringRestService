package com.example.dao.impl;

import com.example.config.DataSource;
import com.example.dao.AuthorDao;
import com.example.entity.Author;
import com.example.entity.Book;
import com.example.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class AuthorDaoImpl implements AuthorDao {

    private static final AuthorDaoImpl INSTANCE = new AuthorDaoImpl();
    private static final String SAVE_SQL = "INSERT INTO authors(name) VALUES ( ? )";
    private static final String FIND_BY_ID_SQL = "SELECT id, name FROM authors WHERE id = ?";
    private static final String FIND_ALL_SQL = "SELECT id, name FROM authors";
    private static final String UPDATE_SQL = "UPDATE authors SET name = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM authors WHERE id = ?";
    private static final String FIND_ALL_BOOKS_BY_AUTHOR_ID = """
            SELECT
                b.id as bookId,
                b.name as bookName
            FROM author_books as ab
            LEFT JOIN books as b ON ab.book_id = b.id
            WHERE ab.author_id = ?
            """;

    private AuthorDaoImpl() {
    }

    public static AuthorDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Author save(Author author) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, author.getName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                author.setId(generatedKeys.getLong("id"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return author;
    }

    @Override
    public Optional<Author> findById(Long id) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Author author = null;
            if (resultSet.next()) {
                author = new Author();
                author.setId(resultSet.getLong("id"));
                author.setName(resultSet.getString("name"));
            }
            return Optional.ofNullable(author);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Author> findAll() {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Author> authors = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                authors.add(new Author(id, name));
            }
            return authors;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Author update(Long id, Author author) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, author.getName());
            preparedStatement.setLong(2, id);
            int resultQuery = preparedStatement.executeUpdate();
            if (resultQuery < 1) {
                throw new NoSuchElementException();
            }
            author.setId(id);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return author;
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Book> findAllBooksByAuthorId(Long id) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BOOKS_BY_AUTHOR_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("bookId"));
                book.setName(resultSet.getString("bookName"));
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return books;
    }

}
