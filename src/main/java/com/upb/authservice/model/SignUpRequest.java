package com.upb.authservice.model;


import lombok.Data;
import lombok.NonNull;

@Data
public class SignUpRequest {
    @NonNull
    private String username;
    @NonNull
    private String email;
    @NonNull
    private String password;
}