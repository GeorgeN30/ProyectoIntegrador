package com.proyecto.proyecto.controller;

import com.proyecto.proyecto.model.dto.BookResponse;
import com.proyecto.proyecto.model.dto.CreateBookRequest;
import com.proyecto.proyecto.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> save(@Valid
                                             @RequestBody
                                             CreateBookRequest request) {

        BookResponse book = bookService.save(request);
        return ResponseEntity.created(URI.create("/api/books/" + book.getId()))
                .body(book);
    }

    @GetMapping
    public List<BookResponse> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BookResponse findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PutMapping("/{id}")
    public BookResponse update(@PathVariable Long id,
                               @Valid
                               @RequestBody
                               CreateBookRequest request) {

        return bookService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }

}
