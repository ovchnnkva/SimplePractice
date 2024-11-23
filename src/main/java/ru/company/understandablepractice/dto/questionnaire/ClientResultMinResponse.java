package ru.company.understandablepractice.dto.questionnaire;

import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Setter
public class ClientResultMinResponse {
    private long id;
    private String questionnaireTitle;
    private LocalDate dateResult;
    private boolean isTest;
}
