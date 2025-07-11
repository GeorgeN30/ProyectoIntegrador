package com.proyecto.proyecto.mapper;

import com.proyecto.proyecto.model.dto.AuthorResponse;
import com.proyecto.proyecto.model.dto.CreateAuthorRequest;
import com.proyecto.proyecto.model.entity.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorResponse toAuthorResponse(Author author);

    Author toAuthor(CreateAuthorRequest request);
}
