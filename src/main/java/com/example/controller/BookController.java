package com.example.controller;

import com.example.dto.BookDto;
import com.example.dto.BookShort;
import com.example.dto.NewBookDto;
import com.example.entity.Book;
import com.example.mapper.BookMapper;
import com.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @Autowired
    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto addBook(@RequestBody NewBookDto newBookDto) {
        Book book = bookMapper.toBook(newBookDto);
        return bookMapper.toBookDto(bookService.addBook(book));
    }

    @GetMapping("/{bookId}")
    public BookDto getBookById(@PathVariable Long bookId) {
        return bookMapper.toBookDto(bookService.getBookById(bookId));
    }

    @GetMapping
    public Set<BookShort> getAllBooks() {
        return bookMapper.toBookShortSet(bookService.getAllBooks());
    }

    @PutMapping("/{bookId}")
    public BookDto updateBook(@PathVariable Long bookId, @RequestBody NewBookDto newBookDto) {
        Book book = bookMapper.toBook(newBookDto);
        return bookMapper.toBookDto(bookService.updateBook(bookId, book));
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
    }
}
