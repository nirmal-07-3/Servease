package com.servease.user.repository;

import com.servease.entity.User;
import com.servease.user.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {


    Optional<UserProfile> findByUser(User user);
}
