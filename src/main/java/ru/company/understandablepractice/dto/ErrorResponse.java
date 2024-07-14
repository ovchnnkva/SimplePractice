package ru.company.understandablepractice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private String systemId;

    private String id;

    private int code;

    private String message;

    private String displayMessage;

    private String techInfo;
}
