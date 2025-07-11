package com.proyecto.proyecto.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "pickup_date")
    private LocalDate pickupDate;

    @ManyToOne
    @JoinColumn(name = "reservation_status_id")
    private ReservationStatus reservationStatus;
}
