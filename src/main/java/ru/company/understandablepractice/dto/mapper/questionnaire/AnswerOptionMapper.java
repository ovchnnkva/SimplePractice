package ru.company.understandablepractice.dto.mapper.questionnaire;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.questionnaire.AnswerOptionDto;
import ru.company.understandablepractice.model.questionnaire.AnswerOption;
import ru.company.understandablepractice.model.questionnaire.Question;

@Mapper(componentModel = "spring")
public abstract class AnswerOptionMapper {


    @Mapping(target = "question", expression = "java(mapQuestion(questionId))")
    public abstract AnswerOption fromDtoToEntity(AnswerOptionDto dto, long questionId);

    public abstract AnswerOptionDto fromEntityToDto(AnswerOption entity);

    Question mapQuestion (long questionId) {
        return new Question(questionId);
    }
}
