package dao;

import entity.Author;
import entity.Book;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BookDao extends Dao<Long, Book> {

    List<Author> findAllAuthorsByBookId(Long bookId, Connection connection) throws SQLException;
    List<Author> findAllAuthorsByBookId(Long bookId);

}
