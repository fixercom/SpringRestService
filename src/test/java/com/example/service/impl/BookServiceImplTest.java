package com.example.service.impl;

import com.example.entity.Author;
import com.example.entity.Book;
import com.example.entity.PublishingHouse;
import com.example.repository.AuthorRepository;
import com.example.repository.BookRepository;
import com.example.repository.PublishingHouseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private PublishingHouseRepository publishingHouseRepository;
    @Mock
    private AuthorRepository authorRepository;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void addBook() {
        Book book = new Book();
        PublishingHouse publishingHouse = new PublishingHouse();
        publishingHouse.setId(33L);
        Author author = new Author();
        author.setId(18L);
        book.setPublishingHouse(publishingHouse);
        book.setAuthors(Set.of(author));
        when(publishingHouseRepository.findById(any(Long.class))).thenReturn(Optional.of(publishingHouse));
        when(authorRepository.findByIdIn(any())).thenReturn(Set.of(author));
        when(bookRepository.save(book)).thenReturn(book);

        bookService.addBook(book);

        InOrder inOrder = inOrder(publishingHouseRepository, authorRepository, bookRepository);
        inOrder.verify(publishingHouseRepository).findById(33L);
        inOrder.verify(authorRepository).findByIdIn(Set.of(18L));
        inOrder.verify(bookRepository).save(book);
    }

    @Test
    void getBookById() {
        when(bookRepository.findByIdWithAuthors(anyLong())).thenReturn(Optional.of(new Book()));

        bookService.getBookById(13L);

        verify(bookRepository).findByIdWithAuthors(13L);
    }

    @Test
    void getAllBooks() {
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        bookService.getAllBooks();

        verify(bookRepository).findAll();
    }

    @Test
    void updateBook() {
        Book book = new Book();
        PublishingHouse publishingHouse = new PublishingHouse();
        publishingHouse.setId(33L);
        Author author = new Author();
        author.setId(18L);
        book.setPublishingHouse(publishingHouse);
        book.setAuthors(Set.of(author));
        when(publishingHouseRepository.findById(any(Long.class))).thenReturn(Optional.of(publishingHouse));
        when(authorRepository.findByIdIn(any())).thenReturn(Set.of(author));
        when(bookRepository.save(book)).thenReturn(book);

        bookService.updateBook(777L, book);

        assertEquals(777L, book.getId());
        InOrder inOrder = inOrder(publishingHouseRepository, authorRepository, bookRepository);
        inOrder.verify(publishingHouseRepository).findById(33L);
        inOrder.verify(authorRepository).findByIdIn(Set.of(18L));
        inOrder.verify(bookRepository).save(book);

    }

    @Test
    void deleteBook() {
        doNothing().when(bookRepository).deleteById(anyLong());

        bookService.deleteBook(11L);

        verify(bookRepository).deleteById(11L);
    }

}