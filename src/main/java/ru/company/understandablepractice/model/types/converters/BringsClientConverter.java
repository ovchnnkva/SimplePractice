package ru.company.understandablepractice.model.types.converters;

import jakarta.persistence.AttributeConverter;
import ru.company.understandablepractice.model.types.BringsClient;

public class BringsClientConverter implements AttributeConverter<BringsClient, String> {
    @Override
    public String convertToDatabaseColumn(BringsClient bringsClient) {
        if (bringsClient == null) return null;
        return bringsClient.getTitle();
    }

    @Override
    public BringsClient convertToEntityAttribute(String s) {
        if (s == null) return null;
        switch (s){
            case "Родитель" -> {
                return BringsClient.PARENT;
            }
            case "Оба родителя" -> {
                return BringsClient.BOTH_PARENTS;
            }
            case "Попечитель" -> {
                return BringsClient.TRUSTEE;
            }
            default -> throw new IllegalArgumentException(s + " not supported.");
        }
    }
}
