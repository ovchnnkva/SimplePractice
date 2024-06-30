package ru.company.understandablepractice.model.types.converters;

import jakarta.persistence.AttributeConverter;
import org.aspectj.apache.bcel.generic.RET;
import ru.company.understandablepractice.model.types.ContactMethod;

public class ContactMethodConverter implements AttributeConverter<ContactMethod, String> {
    @Override
    public String convertToDatabaseColumn(ContactMethod contactMethod) {
        if(contactMethod == null) return null;
        return contactMethod.getTittle();
    }

    @Override
    public ContactMethod convertToEntityAttribute(String s) {
        if(s == null) return null;
        switch (s) {
            case "Мессенджер" -> {
                return ContactMethod.MESSENGER;
            }
            case "Социальная сеть" -> {
                return ContactMethod.SOCIAL_MEDIA;
            }
            case "Агрегатор" -> {
                return ContactMethod.AGGREGATOR;
            }
            default -> throw new IllegalArgumentException(s + " not supported.");
        }
    }
}
