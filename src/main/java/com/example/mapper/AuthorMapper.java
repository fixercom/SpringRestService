package com.example.mapper;

import com.example.dto.AuthorDto;
import com.example.dto.AuthorShort;
import com.example.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper
public interface AuthorMapper {

    AuthorDto toAuthorDto(Author author);

    @Mapping(target = "books", ignore = true)
    Author toAuthor(AuthorDto authorDto);

    Set<AuthorShort> toAuthorShortSet(Set<Author> authorList);

    AuthorShort toAuthorShort(Author author);

}
