package com.proyecto.proyecto.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationKpiDTO {
    private Long currentValue;
    private Double percentageChange;
    private String trend;
}
