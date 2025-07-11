package com.proyecto.proyecto.model.dto;

import com.proyecto.proyecto.util.Type;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CreateReportRequest {

    @NotNull(message = "El id del libro no puede ser nulo.")
    private Long bookId;

    @NotNull(message = "El campo tipo no puede ser nulo.")
    private Type type;

    @NotEmpty(message = "El campo descripción no puede ser vacío o nulo.")
    private String description;

    @NotNull(message = "La fecha de reposición no puede ser nula.")
    private LocalDate restockDate;
    
    private Integer cantidad;
    private Boolean resolved;
}
