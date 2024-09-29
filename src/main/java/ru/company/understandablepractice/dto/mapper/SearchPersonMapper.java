package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.SearchPersonResponse;
import ru.company.understandablepractice.model.Person;
import ru.company.understandablepractice.model.types.ClientStatus;
import ru.company.understandablepractice.model.types.MeetingFormat;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class SearchPersonMapper {

    @Mapping(target = "personId", expression = "java(mapPersonId(person))")
    @Mapping(target = "fullName", expression = "java(mapFullName(person))")
    @Mapping(target = "years", expression = "java(mapYears(person))")
    @Mapping(target = "clientType", expression = "java(mapClientType(person))")
    @Mapping(target = "mail", expression = "java(mapMail(person))")
    @Mapping(target = "phone", expression = "java(mapPhone(person))")
    @Mapping(target = "meetDate", expression = "java(mapMeetDate(meetDates))")
    @Mapping(target = "countMeet", expression = "java(mapMeetCount(meetDates))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatus(person))")
    @Mapping(target = "meetingType", expression = "java(mapMeetingType(person))")
    public abstract SearchPersonResponse fromEntityToResponse(Person person, List<LocalDate> meetDates);

    long mapPersonId(Person person) {
        return person.getId();
    }

    String mapFullName(Person person){
        return person.getFullName();
    }

    int mapYears(Person person){
        return Period.between(Optional.ofNullable(person.getBirth()).orElse(LocalDate.now()), LocalDate.now()).getYears();
    }

    String mapClientType(Person person){
        return person.getClientType().getTittle();
    }

    String mapMail(Person person){
        return person.getMail();
    }

    String mapPhone(Person person){
        return person.getPhoneNumber();
    }

    LocalDate mapMeetDate(List<LocalDate> meetDates){
        return meetDates.stream().findFirst().orElse(null);
    }

    int mapMeetCount(List<LocalDate> meetDates) {
        return meetDates.size();
    }

    String mapClientStatus(Person person){
        return Optional.ofNullable(person.getClientStatus()).map(ClientStatus::getTittle).orElse(null);
    }

    String mapMeetingType(Person person){
        return Optional.ofNullable(person.getMeetingFormat()).map(MeetingFormat::getTittle).orElse(null);
    }
}
