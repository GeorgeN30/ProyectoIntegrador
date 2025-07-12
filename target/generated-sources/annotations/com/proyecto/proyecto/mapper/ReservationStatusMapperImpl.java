package com.proyecto.proyecto.mapper;

import com.proyecto.proyecto.model.dto.CreateReservationStatusRequest;
import com.proyecto.proyecto.model.dto.ReservationStatusResponse;
import com.proyecto.proyecto.model.entity.ReservationStatus;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-11T20:53:11-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class ReservationStatusMapperImpl implements ReservationStatusMapper {

    @Override
    public ReservationStatusResponse toReservationStatusResponse(ReservationStatus reservationStatus) {
        if ( reservationStatus == null ) {
            return null;
        }

        ReservationStatusResponse.ReservationStatusResponseBuilder reservationStatusResponse = ReservationStatusResponse.builder();

        reservationStatusResponse.id( reservationStatus.getId() );
        reservationStatusResponse.status( reservationStatus.getStatus() );

        return reservationStatusResponse.build();
    }

    @Override
    public ReservationStatus toReservationStatus(CreateReservationStatusRequest request) {
        if ( request == null ) {
            return null;
        }

        ReservationStatus reservationStatus = new ReservationStatus();

        reservationStatus.setStatus( request.getStatus() );

        return reservationStatus;
    }
}
