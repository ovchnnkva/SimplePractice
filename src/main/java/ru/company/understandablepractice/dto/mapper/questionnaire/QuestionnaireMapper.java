package ru.company.understandablepractice.dto.mapper.questionnaire;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.questionnaire.QuestionDto;
import ru.company.understandablepractice.dto.questionnaire.QuestionnaireDto;
import ru.company.understandablepractice.dto.questionnaire.QuestionnaireResponse;
import ru.company.understandablepractice.model.questionnaire.Question;
import ru.company.understandablepractice.model.questionnaire.Questionnaire;

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


    public abstract QuestionnaireResponse fromEntityToRequest(Questionnaire entity);

    Set<Question> mapQuestions(QuestionnaireDto dto) {
        return dto.getQuestions()
                .stream()
                .map(questionDto -> questionMapper.fromDtoToEntity(questionDto, dto.getId()))
                .collect(Collectors.toSet());
    }

    Set<QuestionDto> mapQuestionDto(Questionnaire entity) {
        return entity.getQuestions()
                .stream()
                .map(questionDto -> questionMapper.fromEntityToDto(questionDto))
                .collect(Collectors.toSet());
    }
}
