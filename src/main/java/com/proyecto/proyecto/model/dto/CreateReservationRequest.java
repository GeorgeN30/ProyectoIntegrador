package com.proyecto.proyecto.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class CreateReservationRequest {

    @NotNull(message = "El id del usuario no puede ser nulo.")
    private Long userId;

    @NotNull(message = "El id de los libros no puede ser nulo.")
    private List<Long> bookId;

    @NotNull(message = "El campo fecha de retorno no puede ser vacío o nulo.")
    private LocalDate returnDate;

    @NotNull(message = "El campo fecha de recojo no puede ser vacío o nulo.")
    private LocalDate pickupDate;

    @NotNull(message = "El id del estado de la reserva no puede ser nulo.")
    private Long reservationStatusId;
}
