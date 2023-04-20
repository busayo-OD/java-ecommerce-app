package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.user.GetProfileDto;
import com.busayo.ecommercebackend.dto.user.ProfileDto;
import com.busayo.ecommercebackend.dto.user.UpdateProfileDto;
import com.busayo.ecommercebackend.service.ProfileService;
import com.busayo.ecommercebackend.utils.CurrentUserUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/get-profile")
    @ResponseBody
    public ProfileDto getProfile(){
        Long userId = CurrentUserUtil.getCurrentUser().getId();
        return profileService.getProfile(userId);
    }

    @PostMapping("/edit-profile")
    @ResponseBody
    public Boolean editProfile(@RequestBody UpdateProfileDto updateProfileDto){
        Long userId = CurrentUserUtil.getCurrentUser().getId();
        return profileService.editProfile(updateProfileDto, userId);
    }

    @GetMapping
    public ResponseEntity<List<ProfileDto>> getAllProfiles(){
        return ResponseEntity.ok(profileService.getAllProfiles());
    }
}
