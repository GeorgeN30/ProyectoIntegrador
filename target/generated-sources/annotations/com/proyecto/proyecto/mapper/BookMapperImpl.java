package com.proyecto.proyecto.mapper;

import com.proyecto.proyecto.model.dto.BookResponse;
import com.proyecto.proyecto.model.dto.CreateBookRequest;
import com.proyecto.proyecto.model.entity.Book;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-11T20:53:11-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookResponse toBookResponse(Book book) {
        if ( book == null ) {
            return null;
        }

        BookResponse.BookResponseBuilder bookResponse = BookResponse.builder();

        bookResponse.author( book.getAuthor() );
        bookResponse.available( book.getAvailable() );
        bookResponse.category( book.getCategory() );
        bookResponse.description( book.getDescription() );
        bookResponse.id( book.getId() );
        bookResponse.quantity( book.getQuantity() );
        bookResponse.title( book.getTitle() );

        return bookResponse.build();
    }

    @Override
    public Book toBook(CreateBookRequest request) {
        if ( request == null ) {
            return null;
        }

        Book book = new Book();

        book.setAvailable( request.getAvailable() );
        book.setDescription( request.getDescription() );
        book.setQuantity( request.getQuantity() );
        book.setTitle( request.getTitle() );

        return book;
    }
}
