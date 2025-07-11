package com.proyecto.proyecto.model.dto;

import java.math.BigDecimal;

import com.proyecto.proyecto.util.Reason;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePenaltyRequest {

    @NotNull
    private Long userId;  // ID del usuario al que se le aplica la sanción

    @NotNull
    private BigDecimal amount;  // La multa

    @NotNull
    private Reason reason;  // El motivo de la sanción (enum)

    private String description;  // Descripción de la sanción

    @NotNull
    private String suspensionDate;  // Fecha de inicio de la sanción como String (yyyy-MM-dd)

    @NotNull
    private String suspensionEndDate;  // Fecha de fin de la sanción como String (yyyy-MM-dd)

    private Boolean paid = false;  // Estado de la sanción, por defecto "No pagado"
}
