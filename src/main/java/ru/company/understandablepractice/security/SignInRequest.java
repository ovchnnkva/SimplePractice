package ru.company.understandablepractice.security;

import lombok.Data;

@Data
public class SignInRequest {
    private String name;
    private String username;
    private String password;
}
