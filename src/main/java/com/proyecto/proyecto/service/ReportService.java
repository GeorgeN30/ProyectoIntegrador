package com.proyecto.proyecto.service;

import com.proyecto.proyecto.model.dto.CreateReportRequest;
import com.proyecto.proyecto.model.dto.ReportResponse;

import java.util.List;

public interface ReportService {

    ReportResponse save(CreateReportRequest request);
    List<ReportResponse> findAll();
    ReportResponse findById(Long id);
    ReportResponse update(Long id, CreateReportRequest request);
    void deleteById(Long id);


}
