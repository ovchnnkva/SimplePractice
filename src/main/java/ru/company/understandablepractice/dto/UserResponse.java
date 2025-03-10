package ru.company.understandablepractice.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponse {
    private long id;

    private String subscriptionActive;

    private String firstName;

    private String secondName;

    private String lastName;

    private String fullName;

    private String phoneNumber;

    private String city;

    private String mail;

    private String specialization;

    private String professionalActivityDescription;

    private String education;

    private String userImage;

    private List<UserDiplomasResponse> userDiplomasList;
}
