package ru.company.understandablepractice.dto.calendar;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CalendarMeetResponse {
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String formatMeet;

    private String title;
}
