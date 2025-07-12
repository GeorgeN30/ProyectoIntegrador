package com.proyecto.proyecto.mapper;

import com.proyecto.proyecto.model.dto.AuthorResponse;
import com.proyecto.proyecto.model.dto.CreateAuthorRequest;
import com.proyecto.proyecto.model.entity.Author;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-11T20:53:11-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorResponse toAuthorResponse(Author author) {
        if ( author == null ) {
            return null;
        }

        AuthorResponse.AuthorResponseBuilder authorResponse = AuthorResponse.builder();

        authorResponse.id( author.getId() );
        authorResponse.name( author.getName() );

        return authorResponse.build();
    }

    @Override
    public Author toAuthor(CreateAuthorRequest request) {
        if ( request == null ) {
            return null;
        }

        Author author = new Author();

        author.setName( request.getName() );

        return author;
    }
}
