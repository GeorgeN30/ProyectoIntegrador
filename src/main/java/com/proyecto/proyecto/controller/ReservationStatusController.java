package com.proyecto.proyecto.controller;

import com.proyecto.proyecto.model.dto.CreateReservationStatusRequest;
import com.proyecto.proyecto.model.dto.ReservationStatusResponse;
import com.proyecto.proyecto.service.ReservationStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation-statuses")
public class ReservationStatusController {

    private final ReservationStatusService reservationStatusService;

    @PostMapping
    public ResponseEntity<ReservationStatusResponse> save(@Valid
                                                          @RequestBody
                                                          CreateReservationStatusRequest request) {
        ReservationStatusResponse reservationStatus = reservationStatusService.save(request);
        return ResponseEntity.created(URI.create("/api/reservation-statuses/" + reservationStatus.getId()))
                .body(reservationStatus);
    }

    @GetMapping
    public List<ReservationStatusResponse> findAll() {
        return reservationStatusService.findAll();
    }

    @GetMapping("/{id}")
    public ReservationStatusResponse findById(@PathVariable Long id) {
        return reservationStatusService.findById(id);
    }

    @PutMapping("/{id}")
    public ReservationStatusResponse update(@PathVariable Long id,
                                           @Valid
                                           @RequestBody CreateReservationStatusRequest request) {
        return reservationStatusService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        reservationStatusService.deleteById(id);
    }
}
