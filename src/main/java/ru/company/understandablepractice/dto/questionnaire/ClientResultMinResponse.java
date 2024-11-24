package ru.company.understandablepractice.dto.questionnaire;

import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Setter
public class ClientResultMinResponse {
    private long resultId;
    private long questionnaireId;
    private String questionnaireTitle;
    private LocalDate dateResult;
    private boolean isTest;
}
