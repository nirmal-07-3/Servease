package com.servease.user.dto;

import com.servease.entity.enums.UserRole;
import com.servease.user.dto.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String profileImage;
    private LocalDate dateOfBirth;
    private String bio;
    private UserRole role;
    private Gender gender;
    private boolean profileCompleted;



}
