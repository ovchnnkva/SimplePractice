package ru.company.understandablepractice.dto.questionnaire;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import ru.company.understandablepractice.model.questionnaire.Questionnaire;

import java.util.List;

@AllArgsConstructor
@Data
@Setter
public class QuestionnaireListMinResponse {
    private List<QuestionnaireMinResponse> data;

    private long total;
}
