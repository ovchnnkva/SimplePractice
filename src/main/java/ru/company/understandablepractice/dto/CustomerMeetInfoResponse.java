package ru.company.understandablepractice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerMeetInfoResponse {
    private int countMeet;

    private LocalDate lastMeetDate;

    private LocalDate nextMeetDate;
}
