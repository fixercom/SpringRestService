package dao;

import entity.Author;
import entity.Book;

import java.sql.SQLException;
import java.util.List;

public interface AuthorDao extends Dao<Long, Author> {

    List<Book> findAllBooksByAuthorId(Long id) throws SQLException;

}
