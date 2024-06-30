package ru.company.understandablepractice.model.types.converters;

import jakarta.persistence.AttributeConverter;
import ru.company.understandablepractice.model.types.MeetingFormat;

public class MeetingFormatConverter implements AttributeConverter<MeetingFormat, String> {
    @Override
    public String convertToDatabaseColumn(MeetingFormat meetingFormat) {
        if (meetingFormat == null) return null;
        return meetingFormat.getTittle();
    }

    @Override
    public MeetingFormat convertToEntityAttribute(String s) {
        if(s == null) return null;
        switch (s) {
            case "Онлайн" -> {
                return MeetingFormat.ONLINE;
            }
            case "Офлайн" -> {
                return MeetingFormat.OFFLINE;
            }
            default -> throw new IllegalArgumentException(s + " not supported.");
        }
    }
}
