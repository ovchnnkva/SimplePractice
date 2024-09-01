package ru.company.understandablepractice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchPersonResponse {

    private long personId;

    private String fullName;

    private int years;

    private String clientType;

    private String mail;

    private String phone;

    private LocalDate meetDate;

    private int countMeet;

    private String clientStatus;

    private String meetingType;
}
