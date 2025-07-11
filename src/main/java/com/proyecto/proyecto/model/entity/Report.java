package com.proyecto.proyecto.model.entity;

import com.proyecto.proyecto.util.Type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String description;

    @Column(name = "report_date")
    private LocalDate reportDate;

    @Column(name = "restock_date")
    private LocalDate restockDate;

    private Boolean resolved;

    @Column(name = "cantidad")
    private Integer cantidad;
}
