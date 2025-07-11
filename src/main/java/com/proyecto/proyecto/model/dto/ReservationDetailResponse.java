package com.proyecto.proyecto.model.dto;

import com.proyecto.proyecto.model.entity.Book;
import com.proyecto.proyecto.model.entity.Reservation;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationDetailResponse {

    private Long id;
    private Reservation reservation;
    private Book book;
}
