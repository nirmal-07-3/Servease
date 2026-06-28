package com.servease.user.entity;

import com.servease.entity.User;
import com.servease.user.dto.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "user_profiles")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false,unique = true)
    private User user;

    private String bio;



    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate dateOfBirth;
    private boolean profileCompleted=false;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
