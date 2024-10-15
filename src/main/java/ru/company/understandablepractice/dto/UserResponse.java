package ru.company.understandablepractice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private long id;

    private String subscriptionActive;

    private String fullName;

    private String phoneNumber;

    private String city;

    private String mail;

    private String specialization;

    private String professionalActivityDescription;

    private String education;

    private String diplomas;

    private String userImage;
}
