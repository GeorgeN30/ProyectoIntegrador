package com.proyecto.proyecto.repository;

import com.proyecto.proyecto.model.entity.ReservationDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationDetailRepository extends JpaRepository<ReservationDetail, Long> {
    List<ReservationDetail> findAllByReservation_Id(Long reservationId);
}
