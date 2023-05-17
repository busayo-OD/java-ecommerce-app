package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.user.GetProfileDto;
import com.busayo.ecommercebackend.dto.user.ProfileDto;
import com.busayo.ecommercebackend.dto.user.UpdateProfileDto;
import com.busayo.ecommercebackend.service.ProfileService;
import com.busayo.ecommercebackend.utils.CurrentUserUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/my-profile")
    @ResponseBody
    public ProfileDto getProfile(){
        Long userId = CurrentUserUtil.getCurrentUser().getId();
        return profileService.getProfile(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/customer-profile")
    @ResponseBody
    public ProfileDto getCustomerProfile(@RequestBody GetProfileDto getProfileDto){

        return profileService.getCustomerProfile(getProfileDto.getEmail());
    }

    @PostMapping("/edit-profile")
    @ResponseBody
    public Boolean editProfile(@RequestBody UpdateProfileDto updateProfileDto){
        Long userId = CurrentUserUtil.getCurrentUser().getId();
        return profileService.editProfile(updateProfileDto, userId);
    }

}
