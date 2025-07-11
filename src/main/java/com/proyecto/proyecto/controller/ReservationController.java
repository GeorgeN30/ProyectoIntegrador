//Los metodos están al final
package com.proyecto.proyecto.controller;
import com.proyecto.proyecto.model.dto.CreateReservationRequest;
import com.proyecto.proyecto.model.dto.ReservationResponse;
import com.proyecto.proyecto.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> save(@Valid
                                                    @RequestBody
                                                    CreateReservationRequest request) {

        ReservationResponse reservation = reservationService.save(request);
        return ResponseEntity.created(URI.create("/api/reservations/" + reservation.getId()))
                .body(reservation);
    }

    @GetMapping
    public List<ReservationResponse> findAll() {
        return reservationService.findAll();
    }



    //petición para colocar el estado a cancelado
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
    reservationService.cancel(id);
    return ResponseEntity.ok().build();
    }


     //petición para colocar el estado a cofirmado
   @PutMapping("/{id}/confirmed")
    public ResponseEntity<Void> confirmedReservation(@PathVariable Long id) {
    reservationService.confirmed(id);
    return ResponseEntity.ok().build();
    }




}
