package ru.company.understandablepractice.security;

import lombok.Data;

@Data
public class SignInRequest {
    private String firstName;
    private String secondName;
    private String lastName;
    private String email;
    private String username;
    private String password;
}
