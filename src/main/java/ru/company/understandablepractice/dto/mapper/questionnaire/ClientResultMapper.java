package ru.company.understandablepractice.dto.mapper.questionnaire;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.questionnaire.ClientResultResponse;
import ru.company.understandablepractice.model.questionnaire.ClientResult;

import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class ClientResultMapper {

    @Mapping(target = "questionnaireTitle", expression = "java(mapQuestionnaireTitle(entity))")
    @Mapping(target = "test", expression = "java(mapIsTest(entity))")
    public abstract ClientResultResponse fromEntityToResponse(ClientResult entity);

    String mapQuestionnaireTitle(ClientResult entity) {
        return entity.getQuestionnaire().getTitle();
    }

    boolean mapIsTest(ClientResult entity) {
        return entity.getQuestionnaire().isTest();
    }
}
