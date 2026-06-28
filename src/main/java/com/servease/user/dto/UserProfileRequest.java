package com.servease.user.dto;

import com.servease.user.dto.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRequest {

    private LocalDate dateOfBirth;
    private Gender gender;
    private String bio;
}
