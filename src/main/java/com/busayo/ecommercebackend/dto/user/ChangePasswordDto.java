package com.busayo.ecommercebackend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {

    private String email;
    private String existingPassword;
    private String newPassword;
    private String confirmPassword;

}
