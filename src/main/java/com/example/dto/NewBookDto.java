package com.example.dto;

import java.util.Set;

public class NewBookDto {

    private String name;
    private Long publishingHouse;
    Set<Long> authors;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(Long publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public Set<Long> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Long> authors) {
        this.authors = authors;
    }
}
