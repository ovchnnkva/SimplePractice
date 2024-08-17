package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.calendar.CalendarClientDataResponse;
import ru.company.understandablepractice.dto.calendar.CalendarMeetResponse;
import ru.company.understandablepractice.model.Person;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CalendarClientDataMapper {

    @Mapping(target = "meetings", source = "meetings")
    @Mapping(target = "clientType", expression = "java(mapClientType(person))")
    public abstract CalendarClientDataResponse toResponse(Person person, List<CalendarMeetResponse> meetings);

    int mapClientType(Person person) {
        return person.getClientType().getKey();
    }
}
