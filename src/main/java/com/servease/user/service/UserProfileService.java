package com.servease.user.service;


import com.servease.entity.User;
import com.servease.repository.UserRepository;
import com.servease.user.dto.UserProfileRequest;
import com.servease.user.dto.UserProfileResponse;
import com.servease.user.entity.UserProfile;
import com.servease.user.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {


    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserRepository userRepository, UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfileResponse getProfile(String email) {


        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));


        UserProfile userProfile = userProfileRepository.findByUser(user)
                .orElse(null);

        if (userProfile == null) {

            userProfile = new UserProfile();
            userProfile.setUser(user);
            userProfile.setProfileCompleted(false);

            userProfileRepository.save(userProfile);
        }


        UserProfileResponse response = new UserProfileResponse();


        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setProfileImage(user.getProfileImage());
        response.setRole(user.getRole());


        response.setDateOfBirth(userProfile.getDateOfBirth());
        response.setGender(userProfile.getGender());
        response.setBio(userProfile.getBio());
        response.setProfileCompleted(userProfile.isProfileCompleted());


        System.out.println("========== RESPONSE ==========");
        System.out.println("ID = " + response.getId());
        System.out.println("NAME = " + response.getName());
        System.out.println("EMAIL = " + response.getEmail());
        System.out.println("PHONE = " + response.getPhone());
        System.out.println("ROLE = " + response.getRole());
        System.out.println("==============================");

        return response;
    }

    public UserProfileResponse updateProfile(String email, UserProfileRequest request) {


        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile userProfile = userProfileRepository.findByUser(user)
                .orElse(null);

        userProfile.setDateOfBirth(request.getDateOfBirth());
        userProfile.setGender(request.getGender());
        userProfile.setBio(request.getBio());
        userProfile.setProfileCompleted(true);

        userProfileRepository.save(userProfile);

        return new  UserProfileResponse();

    }
}
