package com.proyecto.proyecto.repository;

import com.proyecto.proyecto.model.entity.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
    // Sanciones activas: no pagadas y fecha de fin >= hoy
    @Query("SELECT p FROM Penalty p WHERE p.user.id = :userId AND p.paid = false AND p.suspensionEndDate >= CURRENT_DATE")
    List<Penalty> findActiveByUserId(@Param("userId") Long userId);

    @Query("SELECT p.reason, COUNT(p.id) FROM Penalty p GROUP BY p.reason ORDER BY COUNT(p.id) DESC")
    List<Object[]> countPenaltiesByType();

    @Query("SELECT p.reason, COUNT(p.id) FROM Penalty p " +
            "WHERE p.suspensionDate BETWEEN :startDate AND :endDate " +
            "GROUP BY p.reason ORDER BY COUNT(p.id) DESC")
    List<Object[]> countPenaltiesByTypeInDateRange(LocalDate startDate, LocalDate endDate);

}
