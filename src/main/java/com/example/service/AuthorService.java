package com.example.service;

import com.example.entity.Author;

import java.util.Set;

public interface AuthorService {

    Author addAuthor(Author author);

    Author getAuthorById(Long id);

    Set<Author> getAllAuthors();

    Author updateAuthor(Long id, Author author);

    void deleteAuthor(Long id);

}
