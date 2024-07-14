package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.calendar.CalendarClientDataResponse;
import ru.company.understandablepractice.dto.calendar.CalendarMeetResponse;
import ru.company.understandablepractice.model.Person;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CalendarClientDataMapper {

    @Mapping(target = "fullName", source = "person.fullName")
    @Mapping(target = "firstName", source = "person.firstName")
    @Mapping(target = "secondName", source = "person.secondName")
    @Mapping(target = "lastName", source = "person.lastName")
    @Mapping(target = "meetings", source = "meetings")
    public abstract CalendarClientDataResponse toResponse(Person person, List<CalendarMeetResponse> meetings);
}
