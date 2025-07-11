package com.proyecto.proyecto.mapper;

import com.proyecto.proyecto.model.dto.CreateReservationDetailRequest;
import com.proyecto.proyecto.model.dto.ReservationDetailResponse;
import com.proyecto.proyecto.model.entity.ReservationDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ReservationMapper.class, BookMapper.class})
public interface ReservationDetailMapper {

    ReservationDetailResponse toReservationDetailResponse(ReservationDetail reservationDetail);

    ReservationDetail toReservationDetail(CreateReservationDetailRequest request);
}
