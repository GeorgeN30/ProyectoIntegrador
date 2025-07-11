package com.proyecto.proyecto.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthorResponse {

    private Long id;
    private String name;
}
