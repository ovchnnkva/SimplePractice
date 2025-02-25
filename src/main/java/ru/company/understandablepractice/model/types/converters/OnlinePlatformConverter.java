package ru.company.understandablepractice.model.types.converters;

import jakarta.persistence.AttributeConverter;
import ru.company.understandablepractice.model.types.OnlinePlatform;



public class OnlinePlatformConverter implements AttributeConverter<OnlinePlatform, String> {
    @Override
    public String convertToDatabaseColumn(OnlinePlatform onlinePlatform) {
        if(onlinePlatform == null) return null;
        return onlinePlatform.getTittle();
    }

    @Override
    public OnlinePlatform convertToEntityAttribute(String s) {
        if(s == null) return null;
        switch (s) {
            case "Zoom" -> {
                return OnlinePlatform.ZOOM;
            }
            case "Телемост" -> {
                return OnlinePlatform.TELEMOST;
            }
            case "GoogleMeet" -> {
                return OnlinePlatform.GOOGLE_MEET;
            }
            case "Telegram" -> {
                return OnlinePlatform.TELEGRAM;
            }
            case "WhatsApp" -> {
                return OnlinePlatform.WHATS_APP;
            }
            case "Skype" -> {
                return OnlinePlatform.SKYPE;
            }
            case "Discord" -> {
                return OnlinePlatform.DISCORD;
            }
            default -> throw new IllegalArgumentException(s + " not supported.");
        }
    }
}
