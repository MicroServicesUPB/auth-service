package com.upb.authservice.service.impl;

import com.upb.authservice.entity.ERole;
import com.upb.authservice.entity.User;
import com.upb.authservice.model.JwtAuthenticationResponse;
import com.upb.authservice.model.LoginRequest;
import com.upb.authservice.model.SignUpRequest;
import com.upb.authservice.repository.UserRepository;
import com.upb.authservice.service.AuthenticationService;
import com.upb.authservice.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private  final AuthenticationManager authenticationManager;

    private final JWTService jwtService;



    public User signUp (SignUpRequest signUpRequest){
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(ERole.ROLE_USER);

        return userRepository.save(user);

    }

    public JwtAuthenticationResponse login(LoginRequest loginRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        var user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new IllegalArgumentException("Invalid username or password") );

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);

        return jwtAuthenticationResponse;

    }

}
