package ru.company.understandablepractice.model.types.converters;

import jakarta.persistence.AttributeConverter;
import ru.company.understandablepractice.model.types.Gender;

public class GenderConverter implements AttributeConverter<Gender, String> {

    @Override
    public String convertToDatabaseColumn(Gender gender) {
        if(gender == null) return null;
        return gender.getTittle();
    }

    @Override
    public Gender convertToEntityAttribute(String s) {
        if(s == null) return null;
        switch(s) {
            case "Мужской" -> {
                return Gender.MAN;
            }
            case "Женский" -> {
                return Gender.WOMAN;
            }
            default -> throw new IllegalArgumentException(s + " not supported.");
        }
    }
}
