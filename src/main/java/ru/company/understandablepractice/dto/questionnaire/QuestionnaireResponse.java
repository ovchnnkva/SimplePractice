package ru.company.understandablepractice.dto.questionnaire;

import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Data
@Setter
public class QuestionnaireResponse {
    private long id;
    private String title;
    private boolean isTest;
    private LocalDate dateCreated;
}
