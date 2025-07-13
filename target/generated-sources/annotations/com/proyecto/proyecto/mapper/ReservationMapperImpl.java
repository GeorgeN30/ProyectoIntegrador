package com.proyecto.proyecto.mapper;

import com.proyecto.proyecto.model.dto.CreateReservationRequest;
import com.proyecto.proyecto.model.dto.ReservationResponse;
import com.proyecto.proyecto.model.entity.Reservation;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-12T20:12:05-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class ReservationMapperImpl implements ReservationMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ReservationResponse toReservationResponse(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }

        ReservationResponse.ReservationResponseBuilder reservationResponse = ReservationResponse.builder();

        reservationResponse.id( reservation.getId() );
        reservationResponse.reservationStatus( reservation.getReservationStatus() );
        reservationResponse.user( userMapper.toUserResponse( reservation.getUser() ) );

        reservationResponse.pickupDate( mapFormatPickupDate(reservation) );
        reservationResponse.reservationDate( mapFormatReservationDate(reservation) );
        reservationResponse.returnDate( mapFormatReturnDate(reservation) );

        return reservationResponse.build();
    }

    @Override
    public Reservation toReservation(CreateReservationRequest request) {
        if ( request == null ) {
            return null;
        }

        Reservation reservation = new Reservation();

        reservation.setPickupDate( request.getPickupDate() );
        reservation.setReturnDate( request.getReturnDate() );

        return reservation;
    }
}
