package com.servease.auth.dto;

import com.servease.entity.enums.UserRole;
import lombok.Data;

@Data
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private String phone;
    private UserRole role;
}
