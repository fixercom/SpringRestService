package com.example.dto;

import java.util.Set;

public class BookDto {

    private Long id;
    private String name;
    private PHouseDto publishingHouse;
    private Set<AuthorShort> authors;

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

    public PHouseDto getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(PHouseDto publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public Set<AuthorShort> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorShort> authors) {
        this.authors = authors;
    }
}
