package com.proyecto.proyecto.model.dto;

import com.proyecto.proyecto.model.entity.Book;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadingListResponse {

    private Long id;
    private UserResponse user;
    private Book book;
}
