package com.upb.authservice.controller;

import com.upb.authservice.model.JwtAuthenticationResponse;
import com.upb.authservice.model.LoginRequest;
import com.upb.authservice.model.SignUpRequest;
import com.upb.authservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
@Log4j2
@RequestMapping("/api/auth")
public class AuthenticationController {


    private final AuthenticationService authenticationService;


    @PostMapping("/signup")
    public ResponseEntity<?> authenticateUser(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> registerUser(@RequestBody LoginRequest loginRequest) {
        log.info("Login request for user: {}", loginRequest.getUsername());
        try {
            return ResponseEntity.ok(authenticationService.login(loginRequest));
        } catch (Exception e) {
            log.error("Login failed for user: {}", loginRequest.getUsername(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed");
        }
    }
}
