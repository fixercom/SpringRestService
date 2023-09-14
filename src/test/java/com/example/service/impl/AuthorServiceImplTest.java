package com.example.service.impl;

import com.example.service.impl.AuthorServiceImpl;
import com.example.dao.AuthorDao;
import com.example.entity.Author;
import com.example.entity.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorDao authorDao;
    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    void testAddAuthor() {
        Author author = new Author("name");
        when(authorDao.save(author)).thenReturn(new Author(1L, "name"));

        authorService.addAuthor(author);

        verify(authorDao).save(author);
    }

    @Test
    void testGetAuthorById() {
        when(authorDao.findById(1L)).thenReturn(Optional.of(new Author(1L, "name")));
        when(authorDao.findAllBooksByAuthorId(1L)).thenReturn(List.of(new Book(), new Book()));

        Author author = authorService.getAuthorById(1L);

        assertEquals(2, author.getBooks().size());
        verify(authorDao).findById(1L);
        verify(authorDao).findAllBooksByAuthorId(1L);
    }

    @Test
    void testGetAllAuthors() {
        when(authorDao.findAll()).thenReturn(Collections.emptyList());

        authorService.getAllAuthors();

        verify(authorDao).findAll();
    }

    @Test
    void testUpdateAuthor() {
        Author author = new Author("name");
        when(authorDao.update(1L, author)).thenReturn(new Author(1L, "name"));

        authorService.updateAuthor(1L, author);

        verify(authorDao).update(1L, author);
    }

    @Test
    void testDeleteAuthor() {
        doNothing().when(authorDao).delete(1L);

        authorService.deleteAuthor(1L);

        verify(authorDao).delete(1L);
    }

}