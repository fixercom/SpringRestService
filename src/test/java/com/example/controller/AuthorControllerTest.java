package com.example.controller;

import com.example.dto.AuthorDto;
import com.example.entity.Author;
import com.example.mapper.AuthorMapper;
import com.example.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {

    @Mock
    private AuthorService authorService;
    @Spy
    private AuthorMapper authorMapper = Mappers.getMapper(AuthorMapper.class);
    @InjectMocks
    private AuthorController authorController;

    @Test
    void addAuthor() {
        when(authorService.addAuthor(any(Author.class))).thenReturn(new Author());

        authorController.addAuthor(new AuthorDto());

        InOrder inOrder = Mockito.inOrder(authorMapper, authorService);
        inOrder.verify(authorMapper).toAuthor(any(AuthorDto.class));
        inOrder.verify(authorService).addAuthor(any(Author.class));
        inOrder.verify(authorMapper).toAuthorShort(any(Author.class));
    }

    @Test
    void getAuthorById() {
        when(authorService.getAuthorById(any(Long.class))).thenReturn(new Author());

        authorController.getAuthorById(77L);

        InOrder inOrder = Mockito.inOrder(authorMapper, authorService);
        inOrder.verify(authorService).getAuthorById(77L);
        inOrder.verify(authorMapper).toAuthorDto(any(Author.class));
    }

    @Test
    void getAllAuthors() {
        when(authorService.getAllAuthors()).thenReturn(Collections.emptySet());

        authorController.getAllAuthors();

        InOrder inOrder = Mockito.inOrder(authorMapper, authorService);
        inOrder.verify(authorService).getAllAuthors();
        inOrder.verify(authorMapper).toAuthorShortSet(any());
    }

    @Test
    void updateAuthor() {
        when(authorService.updateAuthor(any(Long.class), any(Author.class))).thenReturn(new Author());

        authorController.updateAuthor(18L, new AuthorDto());

        InOrder inOrder = Mockito.inOrder(authorMapper, authorService);
        inOrder.verify(authorMapper).toAuthor(any(AuthorDto.class));
        inOrder.verify(authorService).updateAuthor(any(Long.class), any(Author.class));
        inOrder.verify(authorMapper).toAuthorShort(any(Author.class));
    }

    @Test
    void deleteAuthor() {
        doNothing().when(authorService).deleteAuthor(any(Long.class));

        authorController.deleteAuthor(13L);

        verify(authorService).deleteAuthor(13L);
    }

}