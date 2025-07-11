package com.proyecto.proyecto.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateBookRequest {

    @NotEmpty(message = "El campo nombre no puede ser vacío o nulo.")
    private String title;

    @NotNull(message = "La cantidad no puede ser nula.")
    private Integer quantity;

    @NotNull(message = "La descripción no puede ser nula.")
    private String description;

    @NotNull(message = "La disponibilidad no puede ser nula.")
    private Boolean available;

    @NotNull(message = "El id de categoría no puede ser nulo.")
    private Long categoryId;

    @NotNull(message = "El id de autor no puede ser nulo")
    private Long authorId;
}
