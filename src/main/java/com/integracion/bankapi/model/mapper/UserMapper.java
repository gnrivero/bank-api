package com.integracion.bankapi.model.mapper;

import com.integracion.bankapi.model.dto.security.UserDTO;
import com.integracion.bankapi.model.security.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private String getName(User user){
        if (user.getClient().getBusinessName() == null){
            return String.format("%s %s", user.getClient().getName(), user.getClient().getLastName());
        } else {
            return user.getClient().getBusinessName();
        }
    }

    public UserDTO toDto(User user) {
        return UserDTO.builder()
                    .username(user.getUsername())
                    .clientId(user.getClient().getId())
                    .cuil(user.getClient().getCuil())
                    .type(user.getType())
                    .name(getName(user))
                    .passwordReset(user.getPasswordReset().toString())
                .build();
    }

}