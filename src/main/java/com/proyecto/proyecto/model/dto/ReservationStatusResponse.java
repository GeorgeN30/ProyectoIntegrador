package com.proyecto.proyecto.model.dto;

import com.proyecto.proyecto.util.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationStatusResponse {

    private Long id;
    private Status status;
}
