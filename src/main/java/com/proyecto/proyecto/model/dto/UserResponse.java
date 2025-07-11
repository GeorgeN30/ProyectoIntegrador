package com.proyecto.proyecto.model.dto;

import com.proyecto.proyecto.util.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private Long id;
    private String name;
    private String lastName;
    private String dni;
    private String email;
    private String password;
    private Role role;
}
