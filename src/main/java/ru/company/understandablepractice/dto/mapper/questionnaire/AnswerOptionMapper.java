package ru.company.understandablepractice.dto.mapper.questionnaire;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.questionnaire.AnswerOptionDto;
import ru.company.understandablepractice.dto.questionnaire.AnswerOptionResponse;
import ru.company.understandablepractice.model.questionnaire.AnswerOption;
import ru.company.understandablepractice.model.questionnaire.Question;

@Mapper(componentModel = "spring")
public abstract class AnswerOptionMapper {


    public abstract AnswerOption fromDtoToEntity(AnswerOptionDto dto, long questionId);

    public abstract AnswerOptionDto fromEntityToDto(AnswerOption entity);

    public abstract AnswerOptionResponse fromEntityToResponse(AnswerOption entity);
}
