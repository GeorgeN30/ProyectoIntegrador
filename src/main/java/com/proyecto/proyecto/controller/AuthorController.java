package com.proyecto.proyecto.controller;

import com.proyecto.proyecto.model.dto.AuthorResponse;
import com.proyecto.proyecto.model.dto.CreateAuthorRequest;
import com.proyecto.proyecto.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorResponse> save(
            @Valid
            @RequestBody CreateAuthorRequest request) {

        AuthorResponse author = authorService.save(request);
        return ResponseEntity.created(URI.create("/api/authors/" + author.getId()))
                .body(author);
    }

    @GetMapping
    public List<AuthorResponse> findAll() {
        return authorService.findAll();
    }

    @GetMapping("/{id}")
    public AuthorResponse findById(@PathVariable Long id) {
        return authorService.findById(id);
    }

    @PutMapping("/{id}")
    public AuthorResponse update(@PathVariable Long id,
                                 @Valid
                                 @RequestBody CreateAuthorRequest request) {

        return authorService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        authorService.deleteById(id);
    }
}


