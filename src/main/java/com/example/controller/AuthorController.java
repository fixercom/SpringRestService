package com.example.controller;

import com.example.dto.AuthorDto;
import com.example.dto.AuthorShort;
import com.example.entity.Author;
import com.example.mapper.AuthorMapper;
import com.example.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorShort addAuthor(@RequestBody AuthorDto authorDto) {
        Author author = authorMapper.toAuthor(authorDto);
        return authorMapper.toAuthorShort(authorService.addAuthor(author));
    }

    @GetMapping("/{authorId}")
    public AuthorDto getAuthorById(@PathVariable Long authorId) {
        return authorMapper.toAuthorDto(authorService.getAuthorById(authorId));
    }

    @GetMapping
    public Set<AuthorShort> getAllAuthors() {
        return authorMapper.toAuthorShortSet(authorService.getAllAuthors());
    }

    @PutMapping("/{authorId}")
    public AuthorShort updateAuthor(@PathVariable Long authorId, @RequestBody AuthorDto authorDto) {
        Author author = authorMapper.toAuthor(authorDto);
        return authorMapper.toAuthorShort(authorService.updateAuthor(authorId, author));
    }

    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable Long authorId) {
        authorService.deleteAuthor(authorId);
    }

}
