package com.proyecto.proyecto.repository;

import com.proyecto.proyecto.model.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
