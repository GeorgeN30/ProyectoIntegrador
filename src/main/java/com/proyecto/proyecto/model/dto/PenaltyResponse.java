package com.proyecto.proyecto.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.proyecto.proyecto.util.Reason;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class PenaltyResponse {

    private Long id;
    private UserResponse user;
    private BigDecimal amount;
    private Reason reason;
    private String description;  // ✅ Agregado

    @JsonFormat(pattern = "yyyy-MM-dd")  // Indicar formato de fecha al devolver la respuesta
    private String suspensionDate;

    @JsonFormat(pattern = "yyyy-MM-dd")  // Indicar formato de fecha al devolver la respuesta
    private String suspensionEndDate;

    private Boolean paid;
}
