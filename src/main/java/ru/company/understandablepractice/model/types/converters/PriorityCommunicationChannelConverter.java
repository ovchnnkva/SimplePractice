package ru.company.understandablepractice.model.types.converters;

import jakarta.persistence.AttributeConverter;
import ru.company.understandablepractice.model.types.PriorityCommunicationChannel;

public class PriorityCommunicationChannelConverter implements AttributeConverter<PriorityCommunicationChannel, String> {
    @Override
    public String convertToDatabaseColumn(PriorityCommunicationChannel priorityCommunicationChannel) {
        if(priorityCommunicationChannel == null) return null;
        return priorityCommunicationChannel.getTittle();
    }

    @Override
    public PriorityCommunicationChannel convertToEntityAttribute(String s) {
        if(s == null) return null;
        switch (s) {
            case "Telegram" -> {
                return PriorityCommunicationChannel.TELEGRAM;
            }
            case "WhatsApp" -> {
                return PriorityCommunicationChannel.WHATS_APP;
            }
            case "Viber" -> {
                return PriorityCommunicationChannel.VIBER;
            }
            case "Skype" -> {
                return PriorityCommunicationChannel.SKYPE;
            }
            case "Почта" -> {
                return PriorityCommunicationChannel.MAIL;
            }
            case "Snapchat" -> {
                return PriorityCommunicationChannel.SNAPCHAT;
            }
            case "Discord" -> {
                return PriorityCommunicationChannel.DISCORD;
            }
            default -> throw new IllegalArgumentException(s + " not supported.");
        }
    }
}
