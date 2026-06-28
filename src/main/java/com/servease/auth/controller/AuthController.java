package com.servease.auth.controller;

import com.servease.auth.dto.ApiResponse;
import com.servease.auth.dto.AuthResponse;
import com.servease.auth.dto.LoginRequest;
import com.servease.auth.dto.RegisterRequest;
import com.servease.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private final AuthService authService;



    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterRequest request) {

        return authService.registerUser(request);

    }
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(
            @RequestBody LoginRequest request) {

        System.out.println("LOGIN API HIT");

        AuthResponse response =
                authService.loginUser(request);

        if(response == null){

            return new ApiResponse<>(
                    false,
                    "Invalid Credentials",
                    null
            );
        }

        return new ApiResponse<>(
                true,
                "Login Successful",
                response
        );
    }
}