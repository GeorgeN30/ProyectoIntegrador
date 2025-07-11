package com.proyecto.proyecto.model.dto;

import com.proyecto.proyecto.util.Status;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateReservationStatusRequest {

    @NotEmpty(message = "El campo estado no puede ser vacío o nulo.")
    private Status status;
}
