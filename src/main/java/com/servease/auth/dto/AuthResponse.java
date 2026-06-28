package com.servease.auth.dto;

import com.servease.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class AuthResponse {

        private Long id;
        private String name;
        private String email;
        private UserRole role;
        private String token;
        private String message;


    }

