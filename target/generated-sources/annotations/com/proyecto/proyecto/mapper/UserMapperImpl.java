package com.proyecto.proyecto.mapper;

import com.proyecto.proyecto.model.dto.CreateUserRequest;
import com.proyecto.proyecto.model.dto.UserResponse;
import com.proyecto.proyecto.model.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-12T20:12:06-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.dni( user.getDni() );
        userResponse.email( user.getEmail() );
        userResponse.id( user.getId() );
        userResponse.lastName( user.getLastName() );
        userResponse.name( user.getName() );
        userResponse.password( user.getPassword() );
        userResponse.role( user.getRole() );

        return userResponse.build();
    }

    @Override
    public User toUser(CreateUserRequest request) {
        if ( request == null ) {
            return null;
        }

        User user = new User();

        user.setDni( request.getDni() );
        user.setEmail( request.getEmail() );
        user.setLastName( request.getLastName() );
        user.setName( request.getName() );
        user.setPassword( request.getPassword() );
        user.setRole( request.getRole() );

        return user;
    }
}
