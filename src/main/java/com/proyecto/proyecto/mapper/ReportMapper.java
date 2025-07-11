package com.proyecto.proyecto.mapper;

import com.proyecto.proyecto.model.dto.CreateReportRequest;
import com.proyecto.proyecto.model.dto.ReportResponse;
import com.proyecto.proyecto.model.entity.Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    @Mapping(target = "reportDate", expression = "java(mapFormatReportDate(report))")
    @Mapping(target = "restockDate", expression = "java(mapFormatRestockDate(report))")
    ReportResponse toReportResponse(Report report);

    Report toReport(CreateReportRequest request);

    default String mapFormatReportDate(Report report) {
        return report.getReportDate()
                     .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    default String mapFormatRestockDate(Report report) {
        return report.getRestockDate()
                     .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
