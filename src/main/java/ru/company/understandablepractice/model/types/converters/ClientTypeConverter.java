package ru.company.understandablepractice.model.types.converters;

import jakarta.persistence.AttributeConverter;
import ru.company.understandablepractice.model.types.ClientType;

public class ClientTypeConverter implements AttributeConverter<ClientType, String> {
    @Override
    public String convertToDatabaseColumn(ClientType clientType) {
        if(clientType == null) return null;
        return clientType.getTittle();
    }

    @Override
    public ClientType convertToEntityAttribute(String s) {
        if(s == null) return null;
        switch (s) {
            case "Пара" -> {
                return ClientType.PAIR;
            }
            case "Взрослый" -> {
                return ClientType.ADULT;
            }
            case "Ребенок" -> {
                return ClientType.CHILD;
            }
            default -> throw new IllegalArgumentException(s + " not supported.");
        }
    }
}
