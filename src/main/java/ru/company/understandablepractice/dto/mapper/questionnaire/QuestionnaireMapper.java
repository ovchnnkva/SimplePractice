package ru.company.understandablepractice.dto.mapper.questionnaire;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.MeetResponse;
import ru.company.understandablepractice.dto.questionnaire.*;
import ru.company.understandablepractice.model.Meet;
import ru.company.understandablepractice.model.questionnaire.Question;
import ru.company.understandablepractice.model.questionnaire.Questionnaire;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class QuestionnaireMapper {

    @Autowired
    QuestionMapper questionMapper;

    @Mapping(target = "questions", expression = "java(mapQuestions(dto))")
    public abstract Questionnaire fromRequestToEntity(QuestionnaireDto dto);

    @Mapping(target = "questions", expression = "java(mapQuestionDto(entity))")
    public abstract QuestionnaireDto fromEntityToDto(Questionnaire entity);

    @Mapping(target = "questions", expression = "java(mapQuestionResponses(entity))")
    public abstract QuestionnaireResponse fromEntityToResponse(Questionnaire entity);

    public abstract QuestionnaireMinResponse fromEntityToMinResponse(Questionnaire entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "questions", ignore = true)
    public abstract void updateEntityFromDto(QuestionnaireDto dto, @MappingTarget Questionnaire entity);

    List<Question> mapQuestions(QuestionnaireDto dto) {
        return dto.getQuestions()
                .stream()
                .map(questionDto -> questionMapper.fromDtoToEntity(questionDto, dto.getId()))
                .collect(Collectors.toList());
    }

    List<QuestionDto> mapQuestionDto(Questionnaire entity) {
        return entity.getQuestions()
                .stream().sorted(Comparator.comparingInt(Question::getOrder))
                .map(questionDto -> questionMapper.fromEntityToDto(questionDto))
                .collect(Collectors.toList());
    }

    List<QuestionResponse> mapQuestionResponses(Questionnaire entity) {
        return entity.getQuestions()
                .stream()
                .map(questionDto -> questionMapper.fromEntityToResponse(questionDto))
                .collect(Collectors.toList());
    }
}
