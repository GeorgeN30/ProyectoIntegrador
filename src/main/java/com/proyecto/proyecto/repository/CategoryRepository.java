package com.proyecto.proyecto.repository;

import com.proyecto.proyecto.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
