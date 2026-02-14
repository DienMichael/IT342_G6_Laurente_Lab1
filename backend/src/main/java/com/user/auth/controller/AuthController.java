package com.user.auth.controller;

import com.user.auth.entity.User;
import com.user.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000") // Required for ReactJS connection
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // 1. POST /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Encrypt the password before saving (Requirement 1.5)
        user.setPassword_hash(encoder.encode(user.getPassword_hash()));

        // Save to MySQL (Requirement 1.4)
        userRepository.save(user);
        return ResponseEntity.ok("Registration success");
    }

    // 2. POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        // Find user by username
        User user = userRepository.findByUsername(loginRequest.getUsername());

        if (user != null && encoder.matches(loginRequest.getPassword_hash(), user.getPassword_hash())) {
            // In a real app, you'd create a session/token here
            return ResponseEntity.ok("Login successful");
        }

        return ResponseEntity.status(401).body("Invalid credentials");
    }
}