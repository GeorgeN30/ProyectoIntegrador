package com.proyecto.proyecto.service;

import com.proyecto.proyecto.exception.UserNotFoundException;
import com.proyecto.proyecto.mapper.UserMapper;
import com.proyecto.proyecto.model.dto.CreateUserRequest;
import com.proyecto.proyecto.model.dto.UserResponse;
import com.proyecto.proyecto.model.entity.User;
import com.proyecto.proyecto.repository.UserRepository;
import com.proyecto.proyecto.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse save(CreateUserRequest request) {
        // Validar DNI único
        if (userRepository.findByDni(request.getDni()).isPresent()) {
            throw new RuntimeException("El DNI ya está registrado");
        }
        // (Opcional: validar email único aquí también)
        User user = userMapper.toUser(request);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserResponse)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public java.util.Optional<UserResponse> findByDni(String dni) {
        return userRepository.findByDni(dni).map(userMapper::toUserResponse);
    }

    @Override
    public UserResponse update(Long id, CreateUserRequest request) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(request.getName());
                    user.setLastName(request.getLastName());
                    user.setDni(request.getDni());
                    user.setEmail(request.getEmail());

                    // Encriptar nueva contraseña
                    user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
                    user.setRole(Role.LECTOR);
                    return userRepository.save(user);
                })
                .map(userMapper::toUserResponse)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserResponse login(String email, String password) {
        User user = userRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (BCrypt.checkpw(password, user.getPassword())) {
            return userMapper.toUserResponse(user);
        } else {
            throw new RuntimeException("Contraseña incorrecta");
        }
    }
}
