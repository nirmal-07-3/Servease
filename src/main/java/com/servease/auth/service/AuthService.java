package com.servease.auth.service;

import com.servease.auth.dto.AuthResponse;
import com.servease.auth.dto.LoginRequest;
import com.servease.auth.dto.RegisterRequest;
import com.servease.entity.User;
import com.servease.entity.enums.UserRole;
import com.servease.entity.enums.UserStatus;
import com.servease.repository.UserRepository;
import com.servease.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Register User
    public String registerUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already registered";
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            return "Phone already registered";
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        if (request.getRole() == UserRole.CUSTOMER) {
            user.setStatus(UserStatus.ACTIVE);
        } else if (request.getRole() == UserRole.PROVIDER) {
            user.setStatus(UserStatus.PENDING);
        } else {
            user.setStatus(UserStatus.ACTIVE);
        }

        userRepository.save(user);

        return "User registered successfully";
    }

    // Login User
    public AuthResponse loginUser(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            return null;
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPasswordHash())) {
            return null;
        }

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole()
        );

        AuthResponse response = new AuthResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setToken(token);
        response.setMessage("Login Successful");

        return response;
    }
}