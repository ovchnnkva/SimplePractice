package ru.company.understandablepractice.dto;

import lombok.Data;

import java.util.List;

@Data
public class ValidationResponse {
    private String name;

    private String message;

    private String displayMessage;

    private List<ParamResponse> params;
}
