package com.integracion.bankapi.service;

import com.integracion.bankapi.model.dto.security.UserDTO;
import com.integracion.bankapi.model.exception.InvalidUserException;
import com.integracion.bankapi.model.mapper.UserMapper;
import com.integracion.bankapi.model.security.Authority;
import com.integracion.bankapi.model.security.User;
import com.integracion.bankapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository repository;
    private UserMapper mapper;

    public User createNewUser(String username){
        User user = new User();
        user.setUsername(username);
        user.setEnabled(true);
        user.setPassword(new BCryptPasswordEncoder().encode("1234"));

        Authority authority = new Authority();
        authority.setAuthority("ROLE_USER");
        //authority.setUser(user);

        //user.setAuthority(authority);

        return repository.save(user);
    }

    public UserDTO authenticateUser(User user) {

        Optional<User> repoUser = repository.findByUsername(user.getUsername());

        if (repoUser.isEmpty())
            throw new InvalidUserException();

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(user.getPassword(), repoUser.get().getPassword())) {
            return mapper.toDto(repoUser.get());
        }

        throw new InvalidUserException();
    }

}