package com.proyecto.proyecto.repository;

import com.proyecto.proyecto.model.entity.ReservationDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationDetailRepository extends JpaRepository<ReservationDetail, Long> {
    List<ReservationDetail> findAllByReservation_Id(Long reservationId);

    @Query("SELECT rd.book.title, COUNT(rd.id) " +
            "FROM ReservationDetail rd " +
            "GROUP BY rd.book.id, rd.book.title " + // Agrupar por ID y título para asegurar unicidad
            "ORDER BY COUNT(rd.id) DESC")
    List<Object[]> findTopNMostReservedBooks(Pageable pageable);


    @Query("SELECT rd.book.author.name, COUNT(rd.id) " +
            "FROM ReservationDetail rd " +
            "GROUP BY rd.book.author.id, rd.book.author.name " + // Agrupar por ID y nombre del autor
            "ORDER BY COUNT(rd.id) DESC")
    List<Object[]> findTopNMostReservedAuthors(Pageable pageable);
}
