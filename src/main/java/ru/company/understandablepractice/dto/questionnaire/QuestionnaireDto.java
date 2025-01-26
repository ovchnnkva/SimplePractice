package ru.company.understandablepractice.dto.questionnaire;

import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Setter
public class QuestionnaireDto {
    private long id;
    private String title;
    private String description;
    private boolean isTest;
    private LocalDate dateCreated;
    private List<QuestionDto> questions;
}
