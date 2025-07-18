package com.proyecto.proyecto.repository;

import com.proyecto.proyecto.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
