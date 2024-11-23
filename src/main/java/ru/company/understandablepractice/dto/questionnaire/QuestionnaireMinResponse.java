package ru.company.understandablepractice.dto.questionnaire;

import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Setter
public class QuestionnaireMinResponse {
    private long id;
    private String title;
    private boolean isTest;
    private LocalDate dateCreated;
}
