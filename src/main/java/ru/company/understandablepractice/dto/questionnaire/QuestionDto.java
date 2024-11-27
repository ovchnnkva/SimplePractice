package ru.company.understandablepractice.dto.questionnaire;

import lombok.Data;
import lombok.Setter;

import java.util.Set;

@Data
@Setter
public class QuestionDto {
    private long id;
    private String type;
    private String text;
    private Set<AnswerOptionDto> answerOptions;
}
