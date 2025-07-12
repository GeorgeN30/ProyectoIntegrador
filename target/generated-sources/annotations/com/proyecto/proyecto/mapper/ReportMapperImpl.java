package com.proyecto.proyecto.mapper;

import com.proyecto.proyecto.model.dto.CreateReportRequest;
import com.proyecto.proyecto.model.dto.ReportResponse;
import com.proyecto.proyecto.model.entity.Report;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-11T20:53:12-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class ReportMapperImpl implements ReportMapper {

    @Override
    public ReportResponse toReportResponse(Report report) {
        if ( report == null ) {
            return null;
        }

        ReportResponse.ReportResponseBuilder reportResponse = ReportResponse.builder();

        reportResponse.book( report.getBook() );
        reportResponse.cantidad( report.getCantidad() );
        reportResponse.description( report.getDescription() );
        reportResponse.id( report.getId() );
        reportResponse.resolved( report.getResolved() );
        reportResponse.type( report.getType() );

        reportResponse.reportDate( mapFormatReportDate(report) );
        reportResponse.restockDate( mapFormatRestockDate(report) );

        return reportResponse.build();
    }

    @Override
    public Report toReport(CreateReportRequest request) {
        if ( request == null ) {
            return null;
        }

        Report report = new Report();

        report.setCantidad( request.getCantidad() );
        report.setDescription( request.getDescription() );
        report.setResolved( request.getResolved() );
        report.setRestockDate( request.getRestockDate() );
        report.setType( request.getType() );

        return report;
    }
}
