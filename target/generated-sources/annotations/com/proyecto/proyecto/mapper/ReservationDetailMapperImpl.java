package com.proyecto.proyecto.mapper;

import com.proyecto.proyecto.model.dto.CreateReservationDetailRequest;
import com.proyecto.proyecto.model.dto.ReservationDetailResponse;
import com.proyecto.proyecto.model.entity.ReservationDetail;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-11T17:31:04-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class ReservationDetailMapperImpl implements ReservationDetailMapper {

    @Override
    public ReservationDetailResponse toReservationDetailResponse(ReservationDetail reservationDetail) {
        if ( reservationDetail == null ) {
            return null;
        }

        ReservationDetailResponse.ReservationDetailResponseBuilder reservationDetailResponse = ReservationDetailResponse.builder();

        reservationDetailResponse.book( reservationDetail.getBook() );
        reservationDetailResponse.id( reservationDetail.getId() );
        reservationDetailResponse.reservation( reservationDetail.getReservation() );

        return reservationDetailResponse.build();
    }

    @Override
    public ReservationDetail toReservationDetail(CreateReservationDetailRequest request) {
        if ( request == null ) {
            return null;
        }

        ReservationDetail reservationDetail = new ReservationDetail();

        return reservationDetail;
    }
}
