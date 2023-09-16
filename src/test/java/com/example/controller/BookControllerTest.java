package com.example.controller;

import com.example.dto.NewBookDto;
import com.example.entity.Book;
import com.example.mapper.BookMapper;
import com.example.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    BookService bookService;
    @Spy
    BookMapper bookMapper = Mappers.getMapper(BookMapper.class);
    @InjectMocks
    BookController bookController;


    @Test
    void addBook() {
        NewBookDto newBookDto = new NewBookDto();
        newBookDto.setAuthors(Set.of(15L,14L));
        when(bookService.addBook(any(Book.class))).thenReturn(new Book());

        bookController.addBook(newBookDto);

        InOrder inOrder = Mockito.inOrder(bookMapper, bookService);
        inOrder.verify(bookMapper).toBook(any(NewBookDto.class));
        inOrder.verify(bookService).addBook(any(Book.class));
        inOrder.verify(bookMapper).toBookDto(any(Book.class));
    }

    @Test
    void getBookById() {
        when(bookService.getBookById(any(Long.class))).thenReturn(new Book());

        bookController.getBookById(77L);

        InOrder inOrder = Mockito.inOrder(bookMapper, bookService);
        inOrder.verify(bookService).getBookById(77L);
        inOrder.verify(bookMapper).toBookDto(any(Book.class));
    }

    @Test
    void getAllBooks() {
        when(bookService.getAllBooks()).thenReturn(Collections.emptySet());

        bookController.getAllBooks();

        InOrder inOrder = Mockito.inOrder(bookMapper, bookService);
        inOrder.verify(bookService).getAllBooks();
        inOrder.verify(bookMapper).toBookShortSet(any());
    }

    @Test
    void updateBook() {
        NewBookDto newBookDto = new NewBookDto();
        newBookDto.setAuthors(Set.of(10L,1L));
        when(bookService.updateBook(any(Long.class), any(Book.class))).thenReturn(new Book());

        bookController.updateBook(18L, newBookDto);

        InOrder inOrder = Mockito.inOrder(bookMapper, bookService);
        inOrder.verify(bookMapper).toBook(any(NewBookDto.class));
        inOrder.verify(bookService).updateBook(any(Long.class), any(Book.class));
        inOrder.verify(bookMapper).toBookDto(any(Book.class));
    }

    @Test
    void deleteBook() {
        doNothing().when(bookService).deleteBook(any(Long.class));

        bookController.deleteBook(13L);

        verify(bookService).deleteBook(13L);
    }

}