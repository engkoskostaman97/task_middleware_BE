package com.belajar.jwt.belajar.jwt.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private Long userid;
    private String token;

    public AuthResponse(Long userid, String token) {
        this.userid = userid;
        this.token = token;
    }
}

