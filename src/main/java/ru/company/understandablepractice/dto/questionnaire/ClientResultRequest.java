package ru.company.understandablepractice.dto.questionnaire;

import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Data
@Setter
public class ClientResultRequest {
    private long id;
    private long questionnaireId;
    private LocalDate dateResult;
    private Set<ClientChoiceRequest> clientChoices;
}
