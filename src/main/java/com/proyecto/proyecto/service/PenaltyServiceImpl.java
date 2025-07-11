package com.proyecto.proyecto.service;

import com.proyecto.proyecto.exception.PenaltyNotFoundException;
import com.proyecto.proyecto.exception.UserNotFoundException;
import com.proyecto.proyecto.mapper.PenaltyMapper;
import com.proyecto.proyecto.model.dto.CreatePenaltyRequest;
import com.proyecto.proyecto.model.dto.PenaltyResponse;
import com.proyecto.proyecto.model.entity.Penalty;
import com.proyecto.proyecto.repository.PenaltyRepository;
import com.proyecto.proyecto.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PenaltyServiceImpl implements PenaltyService{

    @Override
    public List<PenaltyResponse> findActiveByUserId(Long userId) {
        return penaltyRepository.findActiveByUserId(userId)
                .stream()
                .map(penaltyMapper::toPenaltyResponse)
                .collect(Collectors.toList());
    }

    private final PenaltyRepository penaltyRepository;
    private final PenaltyMapper penaltyMapper;
    private final UserRepository userRepository;

@Override
public PenaltyResponse save(CreatePenaltyRequest request) {
    return userRepository.findById(request.getUserId())
            .map(user -> {
                Penalty penalty = new Penalty();
                penalty.setUser(user);
                penalty.setAmount(request.getAmount());
                penalty.setReason(request.getReason());

                // Convertir String a LocalDate
                penalty.setSuspensionDate(LocalDate.parse(request.getSuspensionDate()));  // Aquí se convierte de String a LocalDate
                penalty.setSuspensionEndDate(LocalDate.parse(request.getSuspensionEndDate()));  // Aquí se convierte de String a LocalDate

                // Agregar el detalle de la sanción
                penalty.setDescription(request.getDescription());

                // Estado siempre en false o lo que envíes
                penalty.setPaid(Boolean.FALSE);

                return penaltyRepository.save(penalty);
            })
            .map(penaltyMapper::toPenaltyResponse)
            .orElseThrow(UserNotFoundException::new);
}


    @Override
    public List<PenaltyResponse> findAll() {
        return penaltyRepository.findAll()
                .stream()
                .map(penaltyMapper::toPenaltyResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PenaltyResponse findById(Long id) {
        return penaltyRepository.findById(id)
                .map(penaltyMapper::toPenaltyResponse)
                .orElseThrow(PenaltyNotFoundException::new);
    }

    @Override
    public PenaltyResponse update(Long id, CreatePenaltyRequest request) {
        return penaltyRepository.findById(id)
                .map(penalty -> userRepository
                        .findById(request.getUserId())
                        .map(user -> {
                            penalty.setUser(user);
                            penalty.setAmount(request.getAmount());
                            penalty.setReason(request.getReason());
                            penalty.setPaid(request.getPaid());
                            penalty.setSuspensionEndDate(LocalDate.parse(request.getSuspensionEndDate()));  // Aquí se convierte de String a LocalDate
                            return penaltyRepository.save(penalty);
                        })
                        .orElseThrow(UserNotFoundException::new))
                .map(penaltyMapper::toPenaltyResponse)
                .orElseThrow(PenaltyNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        if(penaltyRepository.findById(id).isEmpty()) {
            throw new PenaltyNotFoundException();
        }
        penaltyRepository.deleteById(id);
    }
}
