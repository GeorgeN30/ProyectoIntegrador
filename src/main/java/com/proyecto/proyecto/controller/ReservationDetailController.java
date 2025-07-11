package com.proyecto.proyecto.controller;

import com.proyecto.proyecto.model.dto.BookResponse;
import com.proyecto.proyecto.model.dto.CreateReservationDetailRequest;
import com.proyecto.proyecto.model.dto.ReservationDetailResponse;
import com.proyecto.proyecto.service.ReservationDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation-details")
public class ReservationDetailController {

    private final ReservationDetailService reservationDetailService;

    @PostMapping
    public ResponseEntity<ReservationDetailResponse> save(@Valid
                                                          @RequestBody
                                                          CreateReservationDetailRequest request) {
        ReservationDetailResponse reservationDetail = reservationDetailService.save(request);
        return ResponseEntity.created(URI.create("/api/reservation-details/" + reservationDetail.getId()))
                .body(reservationDetail);
    }

    @GetMapping("/book/{id}")
    public List<BookResponse> findAllByReservationId(@PathVariable Long id) {
        return reservationDetailService.findAllByReservationId(id);
    }

    @GetMapping
    public List<ReservationDetailResponse> findAll() {
        return reservationDetailService.findAll();
    }
}
