package ru.company.understandablepractice.dto.questionnaire;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class ClientChoiceRequest {
    private long id;
    private long answerOptionId;
    private String text;
}
