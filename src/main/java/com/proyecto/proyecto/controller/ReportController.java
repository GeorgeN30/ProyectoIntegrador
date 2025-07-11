package com.proyecto.proyecto.controller;

import com.proyecto.proyecto.model.dto.CreateReportRequest;
import com.proyecto.proyecto.model.dto.ReportResponse;
import com.proyecto.proyecto.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportResponse> save(@Valid @RequestBody CreateReportRequest request) {
        ReportResponse report = reportService.save(request);
        return ResponseEntity
                .created(URI.create("/api/reports/" + report.getId()))
                .body(report);
    }

    @GetMapping
    public List<ReportResponse> findAll() {
        return reportService.findAll();
    }

    @GetMapping("/{id}")
    public ReportResponse findById(@PathVariable Long id) {
        return reportService.findById(id);
    }

    @PutMapping("/{id}")
    public ReportResponse update(@PathVariable Long id, @Valid @RequestBody CreateReportRequest request) {
        return reportService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        reportService.deleteById(id);
    }
}
