package com.example.dao;

import com.example.entity.Author;
import com.example.entity.Book;

import java.util.List;

public interface BookDao extends Dao<Long, Book> {

    List<Author> findAllAuthorsByBookId(Long bookId);

}
