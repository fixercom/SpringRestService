package com.example.service.impl;

import com.example.entity.Author;
import com.example.entity.Book;
import com.example.entity.PublishingHouse;
import com.example.repository.AuthorRepository;
import com.example.repository.BookRepository;
import com.example.repository.PublishingHouseRepository;
import com.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final PublishingHouseRepository publishingHouseRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository,
                           PublishingHouseRepository publishingHouseRepository,
                           AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.publishingHouseRepository = publishingHouseRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional
    public Book addBook(Book book) {
        setAuthorsAndPublishingHouseForBook(book);
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findByIdWithAuthors(id).orElseThrow();
    }

    @Override
    public Set<Book> getAllBooks() {
        return new LinkedHashSet<>(bookRepository.findAll());
    }

    @Override
    @Transactional
    public Book updateBook(Long id, Book book) {
        book.setId(id);
        setAuthorsAndPublishingHouseForBook(book);
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    private void setAuthorsAndPublishingHouseForBook(Book book) {
        Long publishingHouseId = book.getPublishingHouse().getId();
        Set<Long> authorIds = book.getAuthors().stream().map(Author::getId).collect(Collectors.toSet());
        PublishingHouse publishingHouse = publishingHouseRepository.findById(publishingHouseId).orElseThrow();
        Set<Author> authors = authorRepository.findByIdIn(authorIds);
        book.setPublishingHouse(publishingHouse);
        book.setAuthors(authors);
    }
}
