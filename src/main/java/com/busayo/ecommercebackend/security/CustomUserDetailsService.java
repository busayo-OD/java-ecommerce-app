package com.busayo.ecommercebackend.security;

import com.busayo.ecommercebackend.model.User;
import com.busayo.ecommercebackend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));

        return UserDetailsImpl.build(user);

//        Set<GrantedAuthority> authorities = user
//                .getRoles()
//                .stream()
//                .map((role -> new SimpleGrantedAuthority(role.getTitle()))).collect(Collectors.toSet());
//        return new org.springframework.security.core.userdetails.User(user.getEmail(),
//                user.getPassword(),
//                authorities);
    }
}
