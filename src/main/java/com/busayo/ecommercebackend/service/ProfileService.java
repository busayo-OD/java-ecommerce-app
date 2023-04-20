package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.user.ChangePasswordDto;
import com.busayo.ecommercebackend.dto.user.ProfileDto;
import com.busayo.ecommercebackend.dto.user.UpdateProfileDto;

import java.util.List;

public interface ProfileService {
    ProfileDto getProfile(Long id);
    Boolean editProfile(UpdateProfileDto updateProfileDto, Long id);
    String changePassword(ChangePasswordDto changePasswordDto);
    List<ProfileDto> getAllProfiles();

}
