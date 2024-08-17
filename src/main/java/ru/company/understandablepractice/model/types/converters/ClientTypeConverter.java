package ru.company.understandablepractice.model.types.converters;

import jakarta.persistence.AttributeConverter;
import ru.company.understandablepractice.model.types.ClientType;

public class ClientTypeConverter implements AttributeConverter<ClientType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ClientType clientType) {
        if(clientType == null) return 0;
        return clientType.getKey();
    }

    @Override
    public ClientType convertToEntityAttribute(Integer s) {
        if(s == null) return null;
        switch (s) {
            case 3 -> {
                return ClientType.PAIR;
            }
            case 1 -> {
                return ClientType.ADULT;
            }
            case 2 -> {
                return ClientType.CHILD;
            }
            default -> throw new IllegalArgumentException(s + " not supported.");
        }
    }
}
