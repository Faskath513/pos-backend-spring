package com.pos.backend.controller;

import com.pos.backend.dto.LoginRequest;
import com.pos.backend.model.User;
import com.pos.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (authService.isEmailRegistered(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        User saved = authService.register(user);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (!isAuthenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        // You can return user details or a JWT token here
        return ResponseEntity.ok("Login successful");
    }
}