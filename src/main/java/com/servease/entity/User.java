package com.servease.entity;

import com.servease.entity.enums.UserRole;
import com.servease.entity.enums.UserStatus;
import com.servease.user.dto.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(unique = true,nullable = false)
    private String phone;


    private Boolean emailVerified;
    private Boolean phoneVerified;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime lastLogin;

    @Column(name = "profile_image")
    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private UserStatus status;



}