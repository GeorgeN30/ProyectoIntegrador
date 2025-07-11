package com.proyecto.proyecto.mapper;

import com.proyecto.proyecto.model.dto.CreateReservationStatusRequest;
import com.proyecto.proyecto.model.dto.ReservationStatusResponse;
import com.proyecto.proyecto.model.entity.ReservationStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationStatusMapper {

    ReservationStatusResponse toReservationStatusResponse(ReservationStatus reservationStatus);

    ReservationStatus toReservationStatus(CreateReservationStatusRequest request);
}
