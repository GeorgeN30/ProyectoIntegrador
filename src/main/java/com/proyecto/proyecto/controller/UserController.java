package com.proyecto.proyecto.controller;

import com.proyecto.proyecto.model.dto.CreateUserRequest;
import com.proyecto.proyecto.model.dto.LoginRequest;
import com.proyecto.proyecto.model.dto.UserResponse;
import com.proyecto.proyecto.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody CreateUserRequest request) {
        try {
            UserResponse user = userService.save(request);
            return ResponseEntity.created(URI.create("/api/users/" + user.getId())).body(user);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("DNI ya está registrado")) {
                return ResponseEntity.status(409).body(e.getMessage());
            }
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            UserResponse user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping
    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody CreateUserRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<?> findByDni(@PathVariable String dni) {
    try {
        return userService
            .findByDni(dni)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
    }
    }
}
