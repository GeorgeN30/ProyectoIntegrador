package com.proyecto.proyecto.service;

import com.proyecto.proyecto.model.dto.CreatePenaltyRequest;
import com.proyecto.proyecto.model.dto.PenaltyResponse;

import java.util.List;

public interface PenaltyService {
    List<PenaltyResponse> findActiveByUserId(Long userId);

    PenaltyResponse save(CreatePenaltyRequest request);
    List<PenaltyResponse> findAll();
    PenaltyResponse findById(Long id);
    PenaltyResponse update(Long id, CreatePenaltyRequest request);
    void deleteById(Long id);
}
