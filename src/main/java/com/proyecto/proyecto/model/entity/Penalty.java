package com.proyecto.proyecto.model.entity;

import com.proyecto.proyecto.util.Reason;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@Entity
@Table(name = "penalties")
public class Penalty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal amount;  // La multa

    @Enumerated(EnumType.STRING)
    private Reason reason;  // El motivo de la sanción (enum)

    @DateTimeFormat(pattern = "yyyy-MM-dd")  // Convierte de String a LocalDate
    @Column(name = "suspension_date")
    private LocalDate suspensionDate;  // Fecha de la sanción

    @DateTimeFormat(pattern = "yyyy-MM-dd")  // Convierte de String a LocalDate
    @Column(name = "suspension_end_date")
    private LocalDate suspensionEndDate;  // Fecha de fin de la sanción

    private String description;  // Detalles de la sanción

    private Boolean paid = false;  // Estado de la sanción
}
