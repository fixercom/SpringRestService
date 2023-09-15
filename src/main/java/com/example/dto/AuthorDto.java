package com.example.dto;

import java.util.Set;

public class AuthorDto {

    private Long id;
    private String name;
    private Set<BookShort> books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BookShort> getBooks() {
        return books;
    }

    public void setBooks(Set<BookShort> books) {
        this.books = books;
    }
}
