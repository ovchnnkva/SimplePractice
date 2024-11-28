package ru.company.understandablepractice.dto.questionnaire;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class AnswerOptionDto {
    private long id;
    private String text;
    private boolean isCorrect;
}
