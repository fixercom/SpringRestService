package dao.impl;

import dao.BookDao;
import entity.Author;
import entity.Book;
import entity.PublishingHouse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDaoImpl implements BookDao {

    private static final BookDaoImpl INSTANCE = new BookDaoImpl();
    private static final String SAVE_SQL = "INSERT INTO books(name, publishing_house_id) VALUES ( ?, ? )";
    private static final String SAVE_AUTHORS_SQL = "INSERT INTO author_books (author_id, book_id) VALUES ( ?, ? )";
    private static final String FIND_ALL_SQL = """
            SELECT
                b.id as bookId,
                b.name as bookName,
                ph.id as publishingHouseId,
                ph.name as publishingHouseName
            FROM books as b
            JOIN publishing_houses as ph ON b.publishing_house_id = ph.id
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + "WHERE b.id = ?";
    private static final String FIND_ALL_AUTHORS_BY_BOOK_ID_SQL = """
            SELECT
                a.id as authorId,
                a.name as authorName
            FROM author_books as ab
            LEFT JOIN authors as a ON ab.author_id = a.id
            WHERE ab.book_id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE books
            SET
                name = ?,
                publishing_house_id = ?
            WHERE id = ?            
            """;
    private static final String DELETE_SQL = "DELETE FROM books WHERE id = ?";

    private BookDaoImpl() {
    }

    public static BookDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Book save(Book book, Connection connection) throws SQLException {
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
        return book;
    }

    @Override
    public Optional<Book> findById(Long id, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Book book = null;
            if (resultSet.next()) {
                book = createBook(resultSet);
            }
            return Optional.ofNullable(book);
        }
    }

    @Override
    public List<Book> findAll(Connection connection) throws SQLException {
        List<Book> books = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = createBook(resultSet);
                books.add(book);
            }
        }
        return books;
    }

    @Override
    public Book update(Long id, Book book, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, book.getName());
            preparedStatement.setLong(2, book.getPublishingHouse().getId());
            preparedStatement.setLong(3, id);
            preparedStatement.executeUpdate();
            book.setId(id);
        }
        return book;
    }

    @Override
    public void delete(Long id, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Author> findAllAuthorsByBookId(Long bookId, Connection connection) throws SQLException {
        List<Author> authors = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_AUTHORS_BY_BOOK_ID_SQL)) {
            preparedStatement.setLong(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Author author = new Author();
                author.setId(resultSet.getLong("authorId"));
                author.setName(resultSet.getString("authorName"));
                authors.add(author);
            }
        }
        return authors;
    }

    private static Book createBook(ResultSet resultSet) throws SQLException {
        PublishingHouse publishingHouse = new PublishingHouse();
        publishingHouse.setId(resultSet.getLong("publishingHouseId"));
        publishingHouse.setName(resultSet.getString("publishingHouseName"));
        Book book = new Book();
        book.setId(resultSet.getLong("bookId"));
        book.setName(resultSet.getString("bookName"));
        book.setPublishingHouse(publishingHouse);
        return book;
    }
}
