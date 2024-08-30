package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.SearchPersonResponse;
import ru.company.understandablepractice.model.Meet;
import ru.company.understandablepractice.model.Person;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class SearchPersonMapper {

    @Mapping(target = "fullName", expression = "java(mapFullName(person))")
    @Mapping(target = "years", expression = "java(mapYears(person))")
    @Mapping(target = "clientType", expression = "java(mapClientType(person))")
    @Mapping(target = "mail", expression = "java(mapMail(person))")
    @Mapping(target = "phone", expression = "java(mapPhone(person))")
    @Mapping(target = "meetDate", expression = "java(mapMeetDate(meetDates))")
    @Mapping(target = "meetCount", expression = "java(mapMeetCount(meetDates))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatus(person))")
    @Mapping(target = "meetingType", expression = "java(mapMeetingType(person))")
    public abstract SearchPersonResponse fromEntityToResponse(Person person, List<LocalDate> meetDates);

    String mapFullName(Person person){
        return person.getFullName();
    }

    int mapYears(Person person){
        return Period.between(person.getBirth(), LocalDate.now()).getYears();
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

    LocalDate mapMeetDate(List<LocalDate> meets){
        return Optional.of(meets.getFirst()).orElse(null);
    }

    int mapMeetCount(List<LocalDate> meets) {
        return meets.size();
    }

    String mapClientStatus(Person person){
        return person.getClientStatus().getTittle();
    }

    String mapMeetingType(Person person){
        return person.getMeetingFormat().getTittle();
    }
}
