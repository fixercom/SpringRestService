package com.example.dao;

import com.example.entity.Author;
import com.example.entity.Book;

import java.util.List;

public interface AuthorDao extends Dao<Long, Author> {

    List<Book> findAllBooksByAuthorId(Long id);

}
