package com.proyecto.proyecto.repository;

import com.proyecto.proyecto.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
