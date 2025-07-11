package com.proyecto.proyecto.mapper;

import com.proyecto.proyecto.model.dto.CreateUserRequest;
import com.proyecto.proyecto.model.dto.UserResponse;
import com.proyecto.proyecto.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(User user);

    User toUser(CreateUserRequest request);
}
