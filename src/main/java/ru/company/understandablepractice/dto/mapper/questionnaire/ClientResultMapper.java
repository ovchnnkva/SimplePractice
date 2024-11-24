package ru.company.understandablepractice.dto.mapper.questionnaire;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.questionnaire.ClientResultMinResponse;
import ru.company.understandablepractice.dto.questionnaire.ClientResultRequest;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.questionnaire.ClientChoice;
import ru.company.understandablepractice.model.questionnaire.ClientResult;
import ru.company.understandablepractice.model.questionnaire.Questionnaire;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ClientResultMapper {
    @Autowired
    ClientChoiceMapper clientChoiceMapper;

    @Mapping(target = "questionnaireTitle", expression = "java(mapQuestionnaireTitle(entity))")
    @Mapping(target = "test", expression = "java(mapIsTest(entity))")
    public abstract ClientResultMinResponse fromEntityToResponse(ClientResult entity);

    @Mapping(target = "customer", expression = "java(mapCustomer(request))")
    @Mapping(target = "questionnaire", expression = "java(mapQuestionnaire(request))")
    @Mapping(target = "clientChoices", expression = "java(mapClientChoice(request))")
    public abstract ClientResult fromRequestToEntity(ClientResultRequest request);

    String mapQuestionnaireTitle(ClientResult entity) {
        return entity.getQuestionnaire().getTitle();
    }

    boolean mapIsTest(ClientResult entity) {
        return entity.getQuestionnaire().isTest();
    }

    Customer mapCustomer(ClientResultRequest request) {
        return new Customer(request.getCustomerId());
    }
    Questionnaire mapQuestionnaire(ClientResultRequest request) {
        return new Questionnaire(request.getQuestionnaireId());
    }

    Set<ClientChoice> mapClientChoice(ClientResultRequest request) {
        return request.getClientChoices()
                .stream()
                .map(clientChoiceMapper::fromRequestToEntity)
                .collect(Collectors.toSet());
    }
}
