package dao.impl;

import dao.BookDao;
import entity.Author;
import entity.Book;
import entity.PublishingHouse;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class BookDaoImpl implements BookDao {

    private static final BookDaoImpl INSTANCE = new BookDaoImpl();
    private static final String SAVE_SQL = "INSERT INTO books(name, publish_house_id) VALUES ( ?, ? )";
    private static final String SAVE_AUTHORS_SQL = "INSERT INTO author_books (author_id, book_id) VALUES ( ?, ? )";
    private static final String FIND_BY_ID_SQL = """
            SELECT
                b.id as bookId,
                b.name as bookName,
                ph.id as publishingHouseId,
                ph.name as publishingHouseName
            FROM books as b
            JOIN publish_houses as ph ON b.publish_house_id = ph.id
            WHERE b.id = ?
            """;

    private BookDaoImpl() {
    }

    public static BookDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Book save(Book book, Connection connection) throws SQLException {
        connection.setAutoCommit(false);

        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getName());
            preparedStatement.setLong(2, book.getPublishingHouse().getId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong("id"));
            }
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_AUTHORS_SQL)) {
            List<Long> authorIds = book.getAuthors().stream()
                    .map(Author::getId)
                    .toList();
            for (Long authorId : authorIds) {
                preparedStatement.setLong(1, authorId);
                preparedStatement.setLong(2, book.getId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }

        connection.commit();
        return book;
    }

    @Override
    public Optional<Book> findById(Long id, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Book book = null;
            if (resultSet.next()) {
                PublishingHouse publishingHouse = new PublishingHouse();
                publishingHouse.setId(resultSet.getLong("publishingHouseId"));
                publishingHouse.setName(resultSet.getString("publishingHouseName"));

                book = new Book();
                book.setId(resultSet.getLong("bookId"));
                book.setName(resultSet.getString("bookName"));
                book.setPublishingHouse(publishingHouse);
            }
            return Optional.ofNullable(book);
        }
    }

    @Override
    public List<Book> findAll(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public Book update(Long id, Book book, Connection connection) throws SQLException {
        return null;
    }

    @Override
    public void delete(Long id, Connection connection) throws SQLException {

    }
}
