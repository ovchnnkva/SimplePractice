package ru.company.understandablepractice.dto.mapper.questionnaire;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.questionnaire.ClientChoiceRequest;
import ru.company.understandablepractice.model.questionnaire.AnswerOption;
import ru.company.understandablepractice.model.questionnaire.ClientChoice;

@Mapper(componentModel = "spring")
public abstract class ClientChoiceMapper {

    @Mapping(target = "answerOption", expression = "java(mapAnswerOption(request))")
    public abstract ClientChoice fromRequestToEntity(ClientChoiceRequest request);

    AnswerOption mapAnswerOption(ClientChoiceRequest request) {
        return new AnswerOption(request.getAnswerOptionId());
    }
}
