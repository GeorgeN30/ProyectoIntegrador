package com.proyecto.proyecto.service;

import com.proyecto.proyecto.model.dto.AuthorResponse;
import com.proyecto.proyecto.model.dto.CreateAuthorRequest;

import java.util.List;

public interface AuthorService {

    AuthorResponse save(CreateAuthorRequest request);
    List<AuthorResponse> findAll();
    AuthorResponse findById(Long id);
    AuthorResponse update(Long id, CreateAuthorRequest request);
    void deleteById(Long id);
}
