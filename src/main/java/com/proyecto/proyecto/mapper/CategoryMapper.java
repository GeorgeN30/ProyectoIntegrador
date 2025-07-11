package com.proyecto.proyecto.mapper;

import com.proyecto.proyecto.model.dto.CategoryResponse;
import com.proyecto.proyecto.model.dto.CreateCategoryRequest;
import com.proyecto.proyecto.model.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toCategoryResponse(Category category);

    Category toCategory(CreateCategoryRequest request);
}
