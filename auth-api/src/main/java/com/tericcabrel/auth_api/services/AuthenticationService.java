package com.tericcabrel.auth_api.services;

import com.tericcabrel.auth_api.dtos.LoginUserDto;
import com.tericcabrel.auth_api.dtos.RegisterUserDto;
import com.tericcabrel.auth_api.entities.User;
import com.tericcabrel.auth_api.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public User signUp(RegisterUserDto dto) {
        User user = User.builder().fullName(dto.getFullName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto dto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(),
                dto.getPassword()));


        return userRepository.findByEmail(dto.getEmail()).orElseThrow();
    }
}
