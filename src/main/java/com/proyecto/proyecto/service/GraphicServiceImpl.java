package com.proyecto.proyecto.service;

import com.proyecto.proyecto.model.dto.*;
import com.proyecto.proyecto.repository.BookRepository;
import com.proyecto.proyecto.repository.PenaltyRepository;
import com.proyecto.proyecto.repository.ReservationDetailRepository;
import com.proyecto.proyecto.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GraphicServiceImpl implements GraphicService{

    private final BookRepository bookRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationDetailRepository reservationDetailRepository;
    private final PenaltyRepository penaltyRepository;

    @Override
    public Map<String, Long> getCountBookByCategory() {
        List<Object[]> results = bookRepository.countBooksByCategory();

        return results.stream()
                .collect(Collectors.toMap(
                        array -> (String) array[0],
                        array -> (Long) array[1]
                ));
    }

    @Override
    public Map<String, Long> getCountBookByAvailable() {
        List<Object[]> rawResults = bookRepository.countBooksByAvailable();
        Map<String, Long> availabilityMap = new HashMap<>();

        // Inicializa con 0 para asegurar que siempre existan
        availabilityMap.put("Disponible", 0L);
        availabilityMap.put("No Disponible", 0L);

        for (Object[] result : rawResults) {
            Boolean available = (Boolean) result[0];
            Long count = (Long) result[1];
            if (available != null) { // Asegura que el valor no sea nulo
                if (available) {
                    availabilityMap.put("Disponible", count);
                } else {
                    availabilityMap.put("No Disponible", count);
                }
            }
        }
        return availabilityMap;
    }

    @Override
    public List<ReservationByMonthDTO> getReservationsByMonthRange(Integer numMonths) {
        LocalDate currentDate = LocalDate.now();

        LocalDate startDate = currentDate.minusMonths(numMonths - 1).withDayOfMonth(1);
        LocalDate endDate = currentDate.withDayOfMonth(currentDate.lengthOfMonth()); // Último día del mes actual


        List<Object[]> dbResults = reservationRepository.countReservationsByMonthInRange(startDate, endDate);


        Map<String, Long> reservationsByPeriod = dbResults.stream()
                .collect(Collectors.toMap(
                        array -> String.format("%04d-%02d", (Integer) array[0], (Integer) array[1]), // Clave: "YYYY-MM"
                        array -> (Long) array[2] // Valor: Cantidad de reservas
                ));


        List<ReservationByMonthDTO> completeSeries = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy");

        for (int i = 0; i < numMonths; i++) {
            LocalDate iteratedMonth = startDate.plusMonths(i);
            String periodKey = String.format("%04d-%02d", iteratedMonth.getYear(), iteratedMonth.getMonthValue());
            String periodLabel = iteratedMonth.format(formatter);

            Long quantity = reservationsByPeriod.getOrDefault(periodKey, 0L);

            completeSeries.add(new ReservationByMonthDTO(periodLabel, quantity));
        }
        return completeSeries;
    }

    @Override
    public List<TopReservedBooksDTO> getTopReservedBooks(Integer top) {
        Pageable pageable = PageRequest.of(0, top);

        List<Object[]> rawResults = reservationDetailRepository.findTopNMostReservedBooks(pageable);

        return rawResults.stream()
                .map(array -> new TopReservedBooksDTO((String) array[0], (Long) array[1]))
                .collect(Collectors.toList());
    }

    @Override
    public List<PenaltyByTypeDTO> getPenaltiesByType() {
        List<Object[]> results = penaltyRepository.countPenaltiesByType();

        return results. stream()
                .map(array -> new PenaltyByTypeDTO(
                        ((Enum<?>) array[0]).name(),
                        (Long) array[1]))
                .collect(Collectors.toList());
    }

    @Override
    public List<PenaltyByTypeDTO> getPenaltiesByTypeRange(LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = penaltyRepository.countPenaltiesByTypeInDateRange(startDate, endDate);

        return results.stream()
                .map(array -> new PenaltyByTypeDTO( (String) array[0], (Long) array[1]))
                .collect(Collectors.toList());
    }

    @Override
    public List<TopReservedAuthorsDTO> getTopReservedAuthors(Integer top) {
        Pageable pageable = PageRequest.of(0, top); // Página 0, tamaño topN
        List<Object[]> results = reservationDetailRepository.findTopNMostReservedAuthors(pageable);
        return results.stream()
                .map(array -> new TopReservedAuthorsDTO((String) array[0], (Long) array[1]))
                .collect(Collectors.toList());
    }

    @Override
    public ReservationKpiDTO getMonthlyReservationsKpi() {
        LocalDate today = LocalDate.now();
        YearMonth currentMonthYM = YearMonth.from(today);
        YearMonth previousMonthYM = currentMonthYM.minusMonths(1);

        LocalDate currentMonthStart = currentMonthYM.atDay(1);
        LocalDate currentMonthEnd = currentMonthYM.atEndOfMonth(); // Necesario para el WHERE de la query
        LocalDate previousMonthStart = previousMonthYM.atDay(1);

        List<Object[]> counts = reservationRepository.findCurrentAndPreviousMonthReservationsCounts(
                currentMonthStart, previousMonthStart, currentMonthEnd
        );

        Long currentMonthReservations = 0L;
        Long previousMonthReservations = 0L;
        if (!counts.isEmpty() && counts.get(0) != null) {
            Object[] result = counts.get(0);
            currentMonthReservations = (result[0] == null) ? 0L : ((Number) result[0]).longValue();
            previousMonthReservations = (result[1] == null) ? 0L : ((Number) result[1]).longValue();
        }

        Double percentageChange = 0.0;
        String trend = "flat";

        if (previousMonthReservations > 0) {
            percentageChange = ((double) (currentMonthReservations - previousMonthReservations) / previousMonthReservations) * 100;
        } else if (currentMonthReservations > 0) {
            percentageChange = 100.0;
        }

        if (percentageChange > 0) {
            trend = "up";
        } else if (percentageChange < 0) {
            trend = "down";
        }
        return new ReservationKpiDTO(currentMonthReservations, percentageChange, trend);
    }
}
