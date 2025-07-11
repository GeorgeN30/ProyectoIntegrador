package com.proyecto.proyecto.repository;

import com.proyecto.proyecto.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
