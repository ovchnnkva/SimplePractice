package ru.company.understandablepractice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class MeetResponse {

    private long id;

    private PersonResponse person;

    private String nameMeet;

    private LocalDate dateMeet;

    private LocalTime startMeet;

    private LocalTime endMeet;

    private String formatMeet;

    private String paymentType;

    private LocalDate nextDayMeet;

    private LocalTime nextStartMeet;

    private LocalTime nextEndMeet;

    private String clientSessionRequest;

    private String therapistStateAtSessionStart;

    private String mainTopicsDiscussedDuringSession;

    private String clientKeyPhrasesInsights;

    private String clientMainEmotions;

    private String therapistMainEmotionsExpressed;

    private String therapistUnexpressedEmotions;

    private String techniquesAndMethodsUsed;

    private String clientMainObstaclesMethods;

    private String therapistStateAtSessionEnd;

    private String planNextSession;

    private String supervisionThemAndProblem;
}
