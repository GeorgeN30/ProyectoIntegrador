package com.proyecto.proyecto.service;

import com.proyecto.proyecto.exception.BookNotFoundException;
import com.proyecto.proyecto.exception.ReportNotFoundException;
import com.proyecto.proyecto.mapper.ReportMapper;
import com.proyecto.proyecto.model.dto.CreateReportRequest;
import com.proyecto.proyecto.model.dto.ReportResponse;
import com.proyecto.proyecto.model.entity.Report;
import com.proyecto.proyecto.repository.BookRepository;
import com.proyecto.proyecto.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;
    private final BookRepository bookRepository;

    @Override
    public ReportResponse save(CreateReportRequest request) {
        return bookRepository.findById(request.getBookId())
                .map(book -> {
                    if (request.getCantidad() == null || request.getCantidad() <= 0) {
                        throw new IllegalArgumentException("La cantidad reportada debe ser mayor que cero.");
                    }
                    if (request.getCantidad() > book.getQuantity()) {
                        throw new IllegalArgumentException("La cantidad reportada no puede ser mayor a la cantidad disponible.");
                    }
                    // Restar la cantidad reportada y actualizar disponibilidad
                    int nuevaCantidad = book.getQuantity() - request.getCantidad();
                    book.setQuantity(nuevaCantidad);
                    if (nuevaCantidad <= 0) {
                        book.setAvailable(false);
                    }
                    bookRepository.save(book);

                    Report report = new Report();
                    report.setBook(book);
                    report.setType(request.getType());
                    report.setDescription(request.getDescription());
                    report.setReportDate(LocalDate.now());
                    report.setRestockDate(request.getRestockDate());
                    report.setResolved(request.getResolved());
                    report.setCantidad(request.getCantidad()); // Copiar la cantidad del DTO
                    return reportRepository.save(report);
                })
                .map(reportMapper::toReportResponse)
                .orElseThrow(BookNotFoundException::new);
    }

    @Override
    public List<ReportResponse> findAll() {
        return reportRepository.findAll()
                .stream()
                .map(reportMapper::toReportResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReportResponse findById(Long id) {
        return reportRepository.findById(id)
                .map(reportMapper::toReportResponse)
                .orElseThrow(ReportNotFoundException::new);
    }

    @Override
    public ReportResponse update(Long id, CreateReportRequest request) {
        return reportRepository.findById(id)
                .map(report -> bookRepository.findById(request.getBookId())
                        .map(book -> {
                            // Si el reporte NO estaba resuelto y ahora se resuelve, devolver cantidad
                            boolean wasResolved = Boolean.TRUE.equals(report.getResolved());
                            boolean willBeResolved = Boolean.TRUE.equals(request.getResolved());
                            if (!wasResolved && willBeResolved) {
                                // Sumar la cantidad reportada al stock del libro
                                int nuevaCantidad = book.getQuantity() + report.getCantidad();
                                book.setQuantity(nuevaCantidad);
                                if (nuevaCantidad > 0) {
                                    book.setAvailable(true);
                                }
                                bookRepository.save(book);
                            }

                            report.setBook(book);
                            report.setType(request.getType());
                            report.setDescription(request.getDescription());
                            report.setReportDate(LocalDate.now());
                            report.setRestockDate(request.getRestockDate());
                            report.setResolved(request.getResolved());
                            return reportRepository.save(report);
                        })
                        .orElseThrow(BookNotFoundException::new))
                .map(reportMapper::toReportResponse)
                .orElseThrow(ReportNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        if (reportRepository.findById(id).isEmpty()) {
            throw new ReportNotFoundException();
        }
        reportRepository.deleteById(id);
    }
}
