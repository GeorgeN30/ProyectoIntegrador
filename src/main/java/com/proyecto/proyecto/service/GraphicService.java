package com.proyecto.proyecto.service;

import com.proyecto.proyecto.model.dto.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface GraphicService {

    Map<String, Long> getCountBookByCategory();
    Map<String, Long> getCountBookByAvailable();
    List<ReservationByMonthDTO> getReservationsByMonthRange(Integer numMonths);
    List<TopReservedBooksDTO> getTopReservedBooks(Integer top);
    List<PenaltyByTypeDTO> getPenaltiesByType();
    List<PenaltyByTypeDTO> getPenaltiesByTypeRange(LocalDate startDate, LocalDate endDate);
    List<TopReservedAuthorsDTO> getTopReservedAuthors(Integer top);
    ReservationKpiDTO getMonthlyReservationsKpi();
}
