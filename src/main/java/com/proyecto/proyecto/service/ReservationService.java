package com.proyecto.proyecto.service;

import com.proyecto.proyecto.model.dto.CreateReservationRequest;
import com.proyecto.proyecto.model.dto.ReservationResponse;

import java.util.List;

public interface ReservationService {

    ReservationResponse save(CreateReservationRequest request);
    List<ReservationResponse> findAll();

        //creación de metodos para cancelar y confirmar
    void cancel(Long reservationId);
    void confirmed(Long reservationId);


}
