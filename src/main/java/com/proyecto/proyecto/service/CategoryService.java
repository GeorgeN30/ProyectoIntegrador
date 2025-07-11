package com.proyecto.proyecto.service;

import com.proyecto.proyecto.model.dto.CategoryResponse;
import com.proyecto.proyecto.model.dto.CreateCategoryRequest;

import java.util.List;

public interface CategoryService {

    CategoryResponse save(CreateCategoryRequest request);
    List<CategoryResponse> findAll();
    CategoryResponse findById(Long id);
    CategoryResponse update(Long id, CreateCategoryRequest request);
    void deleteById(Long id);
}
