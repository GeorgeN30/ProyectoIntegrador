package com.proyecto.proyecto.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateReadingListRequest {

    @NotNull(message = "El id de usuario no puede ser nulo o vacío.")
    private Long userId;

    @NotNull(message = "El id de libro no puede ser nulo o vacío.")
    private Long bookId;
}
