package ru.company.understandablepractice.dto.mapper.questionnaire;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.questionnaire.*;
import ru.company.understandablepractice.model.questionnaire.AnswerOption;
import ru.company.understandablepractice.model.questionnaire.Question;
import ru.company.understandablepractice.model.questionnaire.Questionnaire;
import ru.company.understandablepractice.model.types.QuestionType;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class QuestionMapper {

    @Autowired
    AnswerOptionMapper answerOptionMapper;

    @Mapping(target = "questionnaire", expression = "java(mapQuestionnaire(questionnaireId))")
    @Mapping(target = "type", expression = "java(mapType(dto))")
    @Mapping(target = "answerOptions", expression = "java(mapAnswerOptions(dto))")
    public abstract Question fromDtoToEntity(QuestionDto dto, long questionnaireId);

    @Mapping(target = "type", expression = "java(mapTypeString(entity))")
    @Mapping(target = "answerOptions", expression = "java(mapAnswerOptionDto(entity))")
    public abstract QuestionDto fromEntityToDto(Question entity);

    @Mapping(target = "answerOptions", expression = "java(mapAnswerOptionResponse(entity))")
    @Mapping(target = "type", expression = "java(mapTypeString(entity))")
    public abstract QuestionResponse fromEntityToResponse(Question entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "type", expression = "java(updateType(dto, entity))")
    @Mapping(target = "answerOptions", ignore = true)
    public abstract void updateEntityFromDto(QuestionDto dto, @MappingTarget Question entity);

    Questionnaire mapQuestionnaire(long questionnaireId) {
        return new Questionnaire(questionnaireId);
    }

    QuestionType mapType(QuestionDto dto) {
        return Arrays.stream(QuestionType.values())
                .filter(type -> type.getKey().equals(dto.getType()))
                .findFirst()
                .orElse(null);
    }

    QuestionType updateType(QuestionDto dto, Question entity) {
        if (dto.getType() == null) return entity.getType();
        return Arrays.stream(QuestionType.values())
                .filter(type -> type.getKey().equals(dto.getType()))
                .findFirst()
                .orElse(null);
    }

    String mapTypeString(Question entity) {
        return entity.getType() != null ? entity.getType().getKey() : null;
    }

    List<AnswerOption> mapAnswerOptions(QuestionDto dto) {
        return dto.getAnswerOptions()
                .stream()
                .map(questionDto -> answerOptionMapper.fromDtoToEntity(questionDto, dto.getId()))
                .collect(Collectors.toList());
    }

    List<AnswerOptionDto> mapAnswerOptionDto(Question entity) {
        return entity.getAnswerOptions()
                .stream()
                .map(questionDto -> answerOptionMapper.fromEntityToDto(questionDto))
                .collect(Collectors.toList());
    }

    List<AnswerOptionResponse> mapAnswerOptionResponse(Question entity) {
        return entity.getAnswerOptions()
                .stream()
                .map(questionDto -> answerOptionMapper.fromEntityToResponse(questionDto))
                .collect(Collectors.toList());
    }
}
