package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.user.ChangePasswordDto;
import com.busayo.ecommercebackend.dto.user.ProfileDto;
import com.busayo.ecommercebackend.dto.user.UpdateProfileDto;
import com.busayo.ecommercebackend.exception.PasswordMismatchException;
import com.busayo.ecommercebackend.exception.UserNotFoundException;
import com.busayo.ecommercebackend.model.User;
import com.busayo.ecommercebackend.repository.UserRepository;
import com.busayo.ecommercebackend.service.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    private final PasswordEncoder passwordEncoder;

    public ProfileServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ProfileDto getProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return modelMapper.map(user, ProfileDto.class);
    }

    @Override
    public ProfileDto getCustomerProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        return modelMapper.map(user, ProfileDto.class);
    }

    @Override
    public Boolean editProfile(UpdateProfileDto updateProfileDto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!updateProfileDto.getFirstName().isEmpty()){
            user.setFirstName(updateProfileDto.getFirstName());
        }
        if (!updateProfileDto.getLastName().isEmpty()){
            user.setLastName(updateProfileDto.getLastName());
        }
        if (!updateProfileDto.getAddress().isEmpty()){
            user.setAddress(updateProfileDto.getAddress());
        }
        if (!updateProfileDto.getAvatar().isEmpty()){
            user.setAvatar(updateProfileDto.getAvatar());
        }

        if (!updateProfileDto.getCity().isEmpty()){
            user.setCity(updateProfileDto.getCity());
        }

        if (!updateProfileDto.getState().isEmpty()){
            user.setState(updateProfileDto.getState());
        }
        if (!updateProfileDto.getCountry().isEmpty()){
            user.setCountry(updateProfileDto.getCountry());
        }
        if (!updateProfileDto.getPhoneNumber().isEmpty()){
            user.setPhoneNumber(updateProfileDto.getPhoneNumber());
        }

        userRepository.save(user);
        return true;
    }

    @Override
    public String changePassword(ChangePasswordDto changePasswordDto) {
        User user = userRepository.findByEmail(changePasswordDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException(changePasswordDto));
        boolean matches = passwordEncoder.matches(changePasswordDto.getExistingPassword(), user.getPassword());

        if (!matches) {

            throw new PasswordMismatchException();
        }

        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));

        userRepository.save(user);

        return "success";
    }

    @Override
    public List<ProfileDto> getAllProfiles() {
        List<User> users = userRepository.findAll();
        return users.stream().map((profile) -> mapToProfileDto(profile))
                .collect(Collectors.toList());

    }

    private ProfileDto mapToProfileDto (User user) {
        ProfileDto profile = new ProfileDto();
        profile.setId(user.getId());
        profile.setAvatar(user.getAvatar());
        profile.setAddress(user.getAddress());
        profile.setFirstName(user.getFirstName());
        profile.setLastName(user.getLastName());
        profile.setCountry(user.getCountry());
        profile.setPhoneNumber(user.getPhoneNumber());
        profile.setState(user.getState());
        profile.setUsername(user.getUsername());
        profile.setEmail(user.getEmail());
        return profile;
    }

}