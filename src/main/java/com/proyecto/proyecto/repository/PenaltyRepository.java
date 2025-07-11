package com.proyecto.proyecto.repository;

import com.proyecto.proyecto.model.entity.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import com.proyecto.proyecto.model.entity.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
    // Sanciones activas: no pagadas y fecha de fin >= hoy
    @Query("SELECT p FROM Penalty p WHERE p.user.id = :userId AND p.paid = false AND p.suspensionEndDate >= CURRENT_DATE")
    List<Penalty> findActiveByUserId(@Param("userId") Long userId);

}
