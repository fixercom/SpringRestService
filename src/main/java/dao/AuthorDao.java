package dao;

import entity.Author;
import entity.Book;

import java.util.List;

public interface AuthorDao extends Dao<Long, Author> {

    List<Book> findAllBooksByAuthorId(Long id);

}
