package ru.company.understandablepractice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchMeetResponse {
    private long id;

    private String nameMeet;

    private LocalDate dateMeet;

    private String formatMeet;

    private String clientSessionRequest;
}
