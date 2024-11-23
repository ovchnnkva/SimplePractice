package ru.company.understandablepractice.dto.mapper.questionnaire;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.questionnaire.AnswerOptionDto;
import ru.company.understandablepractice.dto.questionnaire.QuestionDto;
import ru.company.understandablepractice.model.questionnaire.AnswerOption;
import ru.company.understandablepractice.model.questionnaire.Question;
import ru.company.understandablepractice.model.questionnaire.Questionnaire;
import ru.company.understandablepractice.model.types.QuestionType;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class QuestionMapper {

    @Autowired
    AnswerOptionMapper answerOptionMapper;

    @Mapping(target = "type", expression = "java(mapType(dto))")
    @Mapping(target = "answerOptions", expression = "java(mapAnswerOptions(dto))")
    public abstract Question fromDtoToEntity(QuestionDto dto);

    @Mapping(target = "type", expression = "java(mapTypeString(entity))")
    public abstract QuestionDto fromEntityToDto(Question entity);

    QuestionType mapType(QuestionDto dto) {
        return Arrays.stream(QuestionType.values())
                .filter(type -> type.getKey().equals(dto.getType()))
                .findFirst()
                .orElse(null);
    }

    String mapTypeString(Question entity) {
        return entity.getType() != null ? entity.getType().getKey() : null;
    }

    Set<AnswerOption> mapAnswerOptions(QuestionDto dto) {
        return dto.getAnswerOptions()
                .stream()
                .map(questionDto -> answerOptionMapper.fromDtoToEntity(questionDto, dto.getId()))
                .collect(Collectors.toSet());
    }

    Set<AnswerOptionDto> mapAnswerOptionDto(Question entity) {
        return entity.getAnswerOptions()
                .stream()
                .map(questionDto -> answerOptionMapper.fromEntityToDto(questionDto))
                .collect(Collectors.toSet());
    }

}
