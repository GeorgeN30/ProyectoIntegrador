package com.proyecto.proyecto.model.dto;

import com.proyecto.proyecto.model.entity.Book;
import com.proyecto.proyecto.util.Type;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportResponse {

    private Long id;
    private Book book;
    private Type type;
    private String description;
    private String reportDate;
    private String restockDate;
    private Integer cantidad;
    private Boolean resolved;
}
