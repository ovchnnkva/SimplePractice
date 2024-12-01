package ru.company.understandablepractice.dto.mapper.questionnaire;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.questionnaire.ClientResultMinResponse;
import ru.company.understandablepractice.dto.questionnaire.ClientResultRequest;
import ru.company.understandablepractice.dto.questionnaire.ClientResultResponse;
import ru.company.understandablepractice.dto.questionnaire.QuestionnaireResponse;
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

    @Autowired
    QuestionnaireMapper questionnaireMapper;

    @Mapping(target = "questionnaireId", expression = "java(mapQuestionnaireId(entity))")
    @Mapping(target = "questionnaireTitle", expression = "java(mapQuestionnaireTitle(entity))")
    @Mapping(target = "test", expression = "java(mapIsTest(entity))")
    @Mapping(target = "resultId", source = "id")
    public abstract ClientResultMinResponse fromEntityToMinResponse(ClientResult entity);

    @Mapping(target = "questionnaire", expression = "java(mapQuestionnaire(request))")
    @Mapping(target = "clientChoices", expression = "java(mapClientChoice(request))")
    public abstract ClientResult fromRequestToEntity(ClientResultRequest request);

    @Mapping(target = "questionnaire", expression = "java(mapQuestionnaireResponse(entity))")
    @Mapping(target = "customerName", expression = "java(mapCustomerName(entity))")
    public abstract ClientResultResponse fromEntityToResponse(ClientResult entity);

    String mapQuestionnaireTitle(ClientResult entity) {
        return entity.getQuestionnaire().getTitle();
    }

    long mapQuestionnaireId(ClientResult entity) {
        return entity.getQuestionnaire().getId();
    }

    boolean mapIsTest(ClientResult entity) {
        return entity.getQuestionnaire().isTest();
    }

    Questionnaire mapQuestionnaire(ClientResultRequest request) {
        return new Questionnaire(request.getQuestionnaireId());
    }

    QuestionnaireResponse mapQuestionnaireResponse(ClientResult entity) {
        return questionnaireMapper.fromEntityToResponse(entity.getQuestionnaire());
    }

    String mapCustomerName(ClientResult entity) {
        return entity.getCustomer() != null ? entity.getCustomer().getFullName() : "";
    }

    Set<ClientChoice> mapClientChoice(ClientResultRequest request) {
        return request.getClientChoices()
                .stream()
                .map(clientChoiceMapper::fromRequestToEntity)
                .collect(Collectors.toSet());
    }
}
