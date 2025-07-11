package com.proyecto.proyecto.service;

import com.proyecto.proyecto.exception.ReservationStatusNotFoundException;
import com.proyecto.proyecto.mapper.ReservationStatusMapper;
import com.proyecto.proyecto.model.dto.CreateReservationStatusRequest;
import com.proyecto.proyecto.model.dto.ReservationStatusResponse;
import com.proyecto.proyecto.model.entity.ReservationStatus;
import com.proyecto.proyecto.repository.ReservationStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationStatusServiceImpl implements  ReservationStatusService{

    private final ReservationStatusRepository repository;
    private final ReservationStatusMapper mapper;

    @Override
    public ReservationStatusResponse save(CreateReservationStatusRequest request) {
        ReservationStatus reservationStatus = mapper.toReservationStatus(request);
        return mapper.toReservationStatusResponse(repository.save(reservationStatus));
    }

    @Override
    public List<ReservationStatusResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toReservationStatusResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationStatusResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toReservationStatusResponse)
                .orElseThrow(ReservationStatusNotFoundException::new);
    }

    @Override
    public ReservationStatusResponse update(Long id, CreateReservationStatusRequest request) {
        return repository.findById(id)
                .map(reservationStatus -> {
                    reservationStatus.setStatus(request.getStatus());
                    return repository.save(reservationStatus);
                })
                .map(mapper::toReservationStatusResponse)
                .orElseThrow(ReservationStatusNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        if(repository.findById(id).isEmpty()){
            throw new ReservationStatusNotFoundException();
        }
        repository.deleteById(id);
    }
}
