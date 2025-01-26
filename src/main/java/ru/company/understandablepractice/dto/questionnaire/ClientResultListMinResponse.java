package ru.company.understandablepractice.dto.questionnaire;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Data
@Setter
public class ClientResultListMinResponse {

    private List<ClientResultMinResponse> data;

    private long total;
}
