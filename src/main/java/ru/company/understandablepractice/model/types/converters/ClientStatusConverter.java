package ru.company.understandablepractice.model.types.converters;

import jakarta.persistence.AttributeConverter;
import ru.company.understandablepractice.model.types.ClientStatus;

public class ClientStatusConverter implements AttributeConverter<ClientStatus, String> {
    @Override
    public String convertToDatabaseColumn(ClientStatus clientStatus) {
        if(clientStatus == null) return null;
        return clientStatus.getTittle();
    }

    @Override
    public ClientStatus convertToEntityAttribute(String s) {
        if(s == null) return null;
        switch (s) {
            case "Регулярный" -> {
                return ClientStatus.REGULAR;
            }
            case "По запросу" -> {
                return ClientStatus.ON_REQUEST;
            }
            case "Завершен" -> {
                return ClientStatus.COMPLETED;
            }
            case "Заявка" -> {
                return ClientStatus.REQUEST;
            }
            default -> throw new IllegalArgumentException(s + " not supported.");
        }
    }
}
