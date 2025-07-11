package com.proyecto.proyecto.service;

import com.proyecto.proyecto.exception.CategoryNotFoundException;
import com.proyecto.proyecto.mapper.CategoryMapper;
import com.proyecto.proyecto.model.dto.CategoryResponse;
import com.proyecto.proyecto.model.dto.CreateCategoryRequest;
import com.proyecto.proyecto.model.entity.Category;
import com.proyecto.proyecto.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse save(CreateCategoryRequest request) {
        Category category = categoryMapper.toCategory(request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toCategoryResponse)
                .orElseThrow(CategoryNotFoundException::new);
    }

    @Override
    public CategoryResponse update(Long id, CreateCategoryRequest request) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(request.getName());
                    return categoryRepository.save(category);
                })
                .map(categoryMapper::toCategoryResponse)
                .orElseThrow(CategoryNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        if(categoryRepository.findById(id).isEmpty()) {
            throw new CategoryNotFoundException();
        }
        categoryRepository.deleteById(id);
    }
}
