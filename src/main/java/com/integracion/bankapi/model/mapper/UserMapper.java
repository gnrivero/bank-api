package com.integracion.bankapi.model.mapper;

import com.integracion.bankapi.model.dto.security.UserDTO;
import com.integracion.bankapi.model.security.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDto(User user) {
        return UserDTO.builder().username(user.getUsername()).build();
    }

}