package ru.company.understandablepractice.model.types.converters;

import jakarta.persistence.AttributeConverter;
import ru.company.understandablepractice.model.types.PriorityCommunicationChannel;
import ru.company.understandablepractice.model.types.QuestionType;

public class QuestionTypeConverter implements AttributeConverter<QuestionType, String> {
    @Override
    public String convertToDatabaseColumn(QuestionType questionType) {
        if(questionType == null) return null;
        return questionType.getKey();
    }

    @Override
    public QuestionType convertToEntityAttribute(String s) {
        if(s == null) return null;
        switch (s) {
            case "Несколько из списка" -> {
                return QuestionType.MANY;
            }
            case "Один из списка" -> {
                return QuestionType.ONE;
            }
            case "Развернутый ответ" -> {
                return QuestionType.DETAILED;
            }
            default -> throw new IllegalArgumentException(s + " not supported.");
        }
    }
}
