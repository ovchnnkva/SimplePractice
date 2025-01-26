package ru.company.understandablepractice.dto.questionnaire;

import lombok.Data;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Data
@Setter
public class QuestionResponse {
    private long id;
    private String type;
    private String text;
    private List<AnswerOptionResponse> answerOptions;
}
