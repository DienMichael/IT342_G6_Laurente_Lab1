package com.user.auth.service;

import com.user.auth.entity.User;
import com.user.auth.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        // Hash the password as per security best practices
        user.setPassword_hash(passwordEncoder.encode(user.getPassword_hash()));
        return userRepository.save(user);
    }
}