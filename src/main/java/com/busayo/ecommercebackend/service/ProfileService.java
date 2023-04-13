package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.user.ChangePasswordDto;
import com.busayo.ecommercebackend.dto.user.ProfileDto;
import com.busayo.ecommercebackend.dto.user.UpdateProfileDto;

import java.util.List;

public interface ProfileService {
    ProfileDto getProfile(String email);
    Boolean editProfile(UpdateProfileDto updateProfileDto);
    String changePassword(ChangePasswordDto changePasswordDto);
    List<ProfileDto> getAllProfiles();

}
