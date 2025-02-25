package ru.company.understandablepractice.dto.mapper.questionnaire;

import org.mapstruct.*;
import ru.company.understandablepractice.dto.questionnaire.AnswerOptionDto;
import ru.company.understandablepractice.dto.questionnaire.AnswerOptionResponse;
import ru.company.understandablepractice.dto.questionnaire.QuestionDto;
import ru.company.understandablepractice.model.questionnaire.AnswerOption;
import ru.company.understandablepractice.model.questionnaire.Question;

@Mapper(componentModel = "spring")
public abstract class AnswerOptionMapper {

    @Mapping(target = "question", expression = "java(mapQuestion(questionId))")
    public abstract AnswerOption fromDtoToEntity(AnswerOptionDto dto, long questionId);

    public abstract AnswerOptionDto fromEntityToDto(AnswerOption entity);

    public abstract AnswerOptionResponse fromEntityToResponse(AnswerOption entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateEntityFromDto(AnswerOptionDto dto, @MappingTarget AnswerOption entity);

    Question mapQuestion(long questionId) {
        return new Question(questionId);
    }
}
