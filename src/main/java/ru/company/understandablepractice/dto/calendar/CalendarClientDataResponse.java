package ru.company.understandablepractice.dto.calendar;

import lombok.Data;

import java.util.List;

@Data
public class CalendarClientDataResponse {
    private String fullName;

    private String firstName;

    private String secondName;

    private String lastName;

    private String clientType;

    private List<CalendarMeetResponse> meetings;
}
