package com.proyecto.proyecto.controller;

import com.proyecto.proyecto.model.dto.CreatePenaltyRequest;
import com.proyecto.proyecto.model.dto.PenaltyResponse;
import com.proyecto.proyecto.service.PenaltyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/penalties")
public class PenaltyController {

    private final PenaltyService penaltyService;

    @PostMapping
    public ResponseEntity<PenaltyResponse> save(@Valid @RequestBody CreatePenaltyRequest request) {
        PenaltyResponse penalty = penaltyService.save(request);
        return ResponseEntity.created(URI.create("/api/penalties/" + penalty.getId()))
                .body(penalty);
    }

    @GetMapping
    public List<PenaltyResponse> findAll() {
        return penaltyService.findAll();
    }

    @GetMapping("/{id}")
    public PenaltyResponse findById(@PathVariable Long id) {
        return penaltyService.findById(id);
    }

    @PutMapping("/{id}")
    public PenaltyResponse update(@PathVariable Long id, @Valid @RequestBody CreatePenaltyRequest request) {
        return penaltyService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        penaltyService.deleteById(id);
    }

    // Endpoint para sanciones activas de un usuario
    @GetMapping("/user/{userId}/active")
    public List<PenaltyResponse> findActiveByUserId(@PathVariable Long userId) {
        return penaltyService.findActiveByUserId(userId);
    }
}
