package com.proyecto.proyecto.service;

import com.proyecto.proyecto.model.dto.BookResponse;
import com.proyecto.proyecto.model.dto.CreateBookRequest;

import java.util.List;

public interface BookService {
    BookResponse save(CreateBookRequest request);
    List<BookResponse> findAll();
    BookResponse findById(Long id);
    BookResponse update(Long id, CreateBookRequest request);
    void deleteById(Long id);
}
