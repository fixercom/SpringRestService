package com.example.service;

import com.example.entity.Author;

import java.util.List;

public interface AuthorService {

    Author addAuthor(Author author);

    Author getAuthorById(Long id);

    List<Author> getAllAuthors();

    Author updateAuthor(Long id, Author author);

    void deleteAuthor(Long id);

}
