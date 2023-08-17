package dao.impl;

import dao.AuthorDao;
import entity.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorDaoImpl implements AuthorDao {

    private static final AuthorDaoImpl INSTANCE = new AuthorDaoImpl();
    private static final String SAVE_SQL = "INSERT INTO authors(name) VALUES ( ? )";
    private static final String FIND_BY_ID_SQL = "SELECT id, name FROM authors WHERE id = ?";
    private static final String FIND_ALL_SQL = "SELECT id, name FROM authors";
    private static final String UPDATE_SQL = "UPDATE authors SET name = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM authors WHERE id = ?";

    private AuthorDaoImpl() {
    }

    public static AuthorDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Author save(Author author, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, author.getName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                author.setId(generatedKeys.getLong("id"));
            }
        }
        return author;
    }

    @Override
    public Optional<Author> findById(Long id, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Author author = null;
            if (resultSet.next()) {
                author = new Author();
                author.setId(resultSet.getLong("id"));
                author.setName(resultSet.getString("name"));
            }
            return Optional.ofNullable(author);
        }
    }

    @Override
    public List<Author> findAll(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Author> authors = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                authors.add(new Author(id, name));
            }
            return authors;
        }
    }

    @Override
    public Author update(Long id, Author author, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, author.getName());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        }
        return author;
    }

    @Override
    public void delete(Long id, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }

}
