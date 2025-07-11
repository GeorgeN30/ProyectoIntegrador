package com.proyecto.proyecto.service;

import com.proyecto.proyecto.model.dto.BookResponse;
import com.proyecto.proyecto.model.dto.CreateReservationDetailRequest;
import com.proyecto.proyecto.model.dto.ReservationDetailResponse;

import java.util.List;

public interface ReservationDetailService {

    ReservationDetailResponse save(CreateReservationDetailRequest request);
    List<BookResponse> findAllByReservationId(Long reservationId);
    List<ReservationDetailResponse> findAll();
}
