package com.servease.user.controller;

import com.servease.user.dto.UserProfileRequest;
import com.servease.user.dto.UserProfileResponse;
import com.servease.user.service.UserProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/profile")
    public UserProfileResponse getProfile(){
        String email="test@gmail.com";

        return userProfileService.getProfile(email);
    }

    @PutMapping("/update")
    public UserProfileResponse updateProfile(@RequestBody UserProfileRequest request){
        String email="test@gmail.com";

        return userProfileService.updateProfile(email,request);
    }

}
