package com.proyecto.proyecto.service;

import com.proyecto.proyecto.model.dto.CreateUserRequest;
import com.proyecto.proyecto.model.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse save(CreateUserRequest request);
    List<UserResponse> findAll();
    UserResponse findById(Long id);
    UserResponse update(Long id, CreateUserRequest request);
    void deleteById(Long id);
    UserResponse login(String email, String password);
    java.util.Optional<UserResponse> findByDni(String dni);

}
