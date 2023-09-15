package com.example.service;

import com.example.entity.Book;

import java.util.Set;

public interface BookService {

    Book addBook(Book book);

    Book getBookById(Long id);

    Set<Book> getAllBooks();

    Book updateBook(Long id, Book book);

    void deleteBook(Long id);

}
