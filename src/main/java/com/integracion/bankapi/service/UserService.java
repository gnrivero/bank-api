package com.integracion.bankapi.service;

import com.integracion.bankapi.model.User;
import com.integracion.bankapi.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository userRepo) {
        this.repository = userRepo;
    }

    public User createNewUser(User user){
        user.setEnabled(true);
        user.setPassword("1234");
        return repository.save(user);
    }

}