package com.proyecto.proyecto.repository;

import com.proyecto.proyecto.model.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationStatusRepository extends JpaRepository<ReservationStatus, Long> {
}
