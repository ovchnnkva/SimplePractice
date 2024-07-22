package ru.company.understandablepractice.dto.leftmenu;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LeftMenuClientReqResponse {
    private LocalDateTime date;

    private String clientName;

    private String clientCardUrl;
}
