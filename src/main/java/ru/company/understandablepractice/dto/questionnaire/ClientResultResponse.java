package ru.company.understandablepractice.dto.questionnaire;

import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Setter
public class ClientResultResponse {
    private long id;
    private String customerName;
    private QuestionnaireResponse questionnaire;
    private LocalDate dateResult;
}
