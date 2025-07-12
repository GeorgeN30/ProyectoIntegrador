package com.proyecto.proyecto.repository;

import com.proyecto.proyecto.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT c.name, COUNT(b.id) FROM Book b JOIN b.category c GROUP BY c.name")
    List<Object[]> countBooksByCategory();

    @Query("SELECT b.available, COUNT(b.id) FROM Book b GROUP BY b.available")
    List<Object[]> countBooksByAvailable();
}
