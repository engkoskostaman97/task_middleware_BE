package com.belajar.jwt.belajar.jwt.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}

