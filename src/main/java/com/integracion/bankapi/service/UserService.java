package com.integracion.bankapi.service;

import com.integracion.bankapi.model.security.Authority;
import com.integracion.bankapi.model.security.User;
import com.integracion.bankapi.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository userRepo) {
        this.repository = userRepo;
    }

    public User createNewUser(String username){
        User user = new User();
        user.setUsername(username);
        user.setEnabled(true);
        user.setPassword(new BCryptPasswordEncoder().encode("1234"));

        Authority authority = new Authority();
        authority.setAuthority("ROLE_USER");
        authority.setUser(user);

        user.setAuthority(authority);

        return repository.save(user);
    }

}