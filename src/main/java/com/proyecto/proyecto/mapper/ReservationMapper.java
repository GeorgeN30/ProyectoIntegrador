package com.proyecto.proyecto.mapper;

import com.proyecto.proyecto.model.dto.CreateReservationRequest;
import com.proyecto.proyecto.model.dto.ReservationResponse;
import com.proyecto.proyecto.model.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, ReservationStatusMapper.class})
public interface ReservationMapper {

    @Mapping(target = "pickupDate", expression = "java(mapFormatPickupDate(reservation))")
    @Mapping(target = "reservationDate", expression = "java(mapFormatReservationDate(reservation))")
    @Mapping(target = "returnDate", expression = "java(mapFormatReturnDate(reservation))")
    ReservationResponse toReservationResponse(Reservation reservation);

    Reservation toReservation(CreateReservationRequest request);
    
    default String mapFormatReservationDate(Reservation reservation) {
        return reservation
                .getReservationDate()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    default String mapFormatReturnDate(Reservation reservation) {
        return reservation
                .getReturnDate()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    default String mapFormatPickupDate(Reservation reservation) {
        return reservation
                .getPickupDate()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
