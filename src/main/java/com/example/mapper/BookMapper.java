package com.example.mapper;

import com.example.dto.BookDto;
import com.example.dto.BookShort;
import com.example.dto.NewBookDto;
import com.example.entity.Author;
import com.example.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.LinkedHashSet;
import java.util.Set;

@Mapper
public interface BookMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishingHouse.id", source = "publishingHouse")
    @Mapping(target = "authors", expression = "java(toAuthorsFromAuthorIds(newBookDto.getAuthors()))")
    Book toBook(NewBookDto newBookDto);

    BookShort toBookShort(Book book);

    Set<BookShort> toBookShortSet(Set<Book> books);

    BookDto toBookDto(Book book);

    default Set<Author> toAuthorsFromAuthorIds(Set<Long> authorIds) {
        Set<Author> authors = new LinkedHashSet<>();
        for (Long authorId : authorIds) {
            Author author = new Author();
            author.setId(authorId);
            authors.add(author);
        }
        return authors;
    }

}
