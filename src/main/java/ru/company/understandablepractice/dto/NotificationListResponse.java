package ru.company.understandablepractice.dto;

import lombok.Data;

import java.util.List;

@Data
public class NotificationListResponse {

    private int count;

    private List<NotificationResponse> notificationResponseList;
}
