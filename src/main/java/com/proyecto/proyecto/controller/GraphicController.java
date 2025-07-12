package com.proyecto.proyecto.controller;

import com.proyecto.proyecto.model.dto.*;
import com.proyecto.proyecto.service.GraphicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/graphics")
@RequiredArgsConstructor
public class GraphicController {

    private final GraphicService graphicService;

    @GetMapping("/bar/book-category")
    public Map<String, Long> getBookByCategory(){
        return graphicService.getCountBookByCategory();
    }

    @GetMapping("/circle/book-available")
    public Map<String, Long> getBookByAvailable(){
        return graphicService.getCountBookByAvailable();
    }

    @GetMapping("/lineal/reservation-month")
    public List<ReservationByMonthDTO> getReservationsByMonthRange(
            @RequestParam(defaultValue = "6") int numMonths) {
        return graphicService.getReservationsByMonthRange(numMonths);
    }

    @GetMapping("/bar/top-reserved-books")
    public List<TopReservedBooksDTO> getTopReservedBooks( @RequestParam(defaultValue = "5") int top) {
        return graphicService.getTopReservedBooks(top);
    }

    @GetMapping("/bar/penalties-by-type")
    public List<PenaltyByTypeDTO> getPenaltiesByType() {
        return graphicService.getPenaltiesByType();
    }

    @GetMapping("/bar/top-reserved-authors")
    public List<TopReservedAuthorsDTO> getTopReservedAuthors( @RequestParam(defaultValue = "5") int top) {
        return graphicService.getTopReservedAuthors(top);
    }

    @GetMapping("/kpi/monthly-reservations")
    public ReservationKpiDTO getMonthlyReservationsKpi() {
        return graphicService.getMonthlyReservationsKpi();
    }
}
