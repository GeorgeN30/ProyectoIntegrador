package com.proyecto.proyecto.service;

import com.proyecto.proyecto.model.dto.CreateReservationStatusRequest;
import com.proyecto.proyecto.model.dto.ReservationStatusResponse;

import java.util.List;

public interface ReservationStatusService {

    ReservationStatusResponse save(CreateReservationStatusRequest request);
    List<ReservationStatusResponse> findAll();
    ReservationStatusResponse findById(Long id);
    ReservationStatusResponse update(Long id, CreateReservationStatusRequest request);
    void deleteById(Long id);

}
