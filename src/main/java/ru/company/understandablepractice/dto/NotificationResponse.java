package ru.company.understandablepractice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NotificationResponse {

    private LocalDate dateFirstRequest;

    private String customerFullName;
}
