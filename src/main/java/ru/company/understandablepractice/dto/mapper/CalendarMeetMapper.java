package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.MeetResponse;
import ru.company.understandablepractice.dto.calendar.CalendarMeetResponse;
import ru.company.understandablepractice.model.Meet;
import ru.company.understandablepractice.model.types.MeetingFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Mapper(componentModel = "spring")
public abstract class CalendarMeetMapper {

    @Mapping(target = "startTime", expression = "java(mapStartTime(entity))")
    @Mapping(target = "endTime", expression = "java(mapEndTime(entity))")
    @Mapping(target = "formatMeet", expression = "java(mapFormatMeet(entity))")
    public abstract CalendarMeetResponse fromEntityToResponse(Meet entity);

    LocalDateTime mapStartTime(Meet entity) {
        return LocalDateTime.of(entity.getDateMeet(), entity.getStartMeet());
    }

    LocalDateTime mapEndTime(Meet entity) {
        return LocalDateTime.of(entity.getDateMeet(), entity.getEndMeet());
    }

    String mapFormatMeet(Meet entity) {
        return entity.getFormatMeet().getTittle();
    }
}
