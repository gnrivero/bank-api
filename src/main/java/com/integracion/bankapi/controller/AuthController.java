package com.integracion.bankapi.controller;

import com.integracion.bankapi.model.security.User;
import com.integracion.bankapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<?> authenticateUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.authenticateUser(user));
    }

}