package com.example.service.impl;

import com.example.entity.Author;
import com.example.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;
    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    void addAuthor() {
        when(authorRepository.save(any(Author.class))).thenReturn(new Author());

        authorService.addAuthor(new Author());

        verify(authorRepository).save(any(Author.class));
    }

    @Test
    void getAuthorById() {
        when(authorRepository.findByIdWithBooks(any(Long.class))).thenReturn(Optional.of(new Author()));

        authorService.getAuthorById(1L);

        verify(authorRepository).findByIdWithBooks(1L);
    }

    @Test
    void getAllAuthors() {
        when(authorRepository.findAll()).thenReturn(Collections.emptyList());

        authorService.getAllAuthors();

        verify(authorRepository).findAll();
    }

    @Test
    void updateAuthor() {
        Author author = new Author();
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        authorService.updateAuthor(99L, author);


        assertEquals(99L, author.getId());
        verify(authorRepository).save(any(Author.class));
    }

    @Test
    void deleteAuthor() {
        doNothing().when(authorRepository).deleteById(anyLong());

        authorService.deleteAuthor(147L);

        verify(authorRepository).deleteById(147L);
    }

}