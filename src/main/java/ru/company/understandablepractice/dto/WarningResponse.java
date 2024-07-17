package ru.company.understandablepractice.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WarningResponse {
    private List<ValidationResponse> validations = new ArrayList<>();
}
