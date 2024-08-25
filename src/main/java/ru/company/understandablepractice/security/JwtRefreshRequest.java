package ru.company.understandablepractice.security;

import lombok.Data;

@Data
public class JwtRefreshRequest {
    private String refreshToken;
}
