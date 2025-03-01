package ru.company.understandablepractice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CustomerMeetInfoResponse {
    private int countMeet;

    private LocalDate lastMeetDate;

    private LocalTime lastMeetStart;

    private LocalTime lastMeetEnd;

    private LocalDate nextMeetDate;

    private LocalTime nextMeetStart;

    private LocalTime nextMeetEnd;
}
