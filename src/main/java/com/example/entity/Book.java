package com.example.entity;

import java.util.ArrayList;
import java.util.List;

public class Book {

    Long id;
    String name;
    PublishingHouse publishingHouse;
    List<Author> authors = new ArrayList<>();

    public Book() {
    }

    public Book(String name, PublishingHouse publishingHouse) {
        this.name = name;
        this.publishingHouse = publishingHouse;
    }

    public Book(String name, PublishingHouse publishingHouse, List<Author> authors) {
        this.name = name;
        this.publishingHouse = publishingHouse;
        this.authors = authors;
    }

    public Book(Long id, String name, PublishingHouse publishingHouse) {
        this.id = id;
        this.name = name;
        this.publishingHouse = publishingHouse;
    }

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

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public PublishingHouse getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(PublishingHouse publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", publishingHouse=" + publishingHouse +
                ", authors=" + authors +
                '}';
    }
}
