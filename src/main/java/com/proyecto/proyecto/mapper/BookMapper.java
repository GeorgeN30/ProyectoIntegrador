package com.proyecto.proyecto.mapper;

import com.proyecto.proyecto.model.dto.BookResponse;
import com.proyecto.proyecto.model.dto.CreateBookRequest;
import com.proyecto.proyecto.model.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, AuthorMapper.class})
public interface BookMapper {

    BookResponse toBookResponse(Book book);

    Book toBook(CreateBookRequest request);
}
