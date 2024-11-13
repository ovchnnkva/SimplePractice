package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.PersonResponse;
import ru.company.understandablepractice.model.Person;
import ru.company.understandablepractice.model.types.ClientStatus;
import ru.company.understandablepractice.model.types.ClientType;
import ru.company.understandablepractice.model.types.Gender;
import ru.company.understandablepractice.model.types.MeetingFormat;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Mapper(componentModel = "spring")
public abstract class PersonMapper {

    @Mapping(target = "personCredentials", ignore = true)
    @Mapping(target = "applicationFormToken", ignore = true)
    @Mapping(target = "applicationFormStatus", ignore = true)
    @Mapping(target = "clientType", expression = "java(mapClientType(response))")
    @Mapping(target = "gender", expression = "java(mapGender(response))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatus(response))")
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormat(response))")
    @Mapping(target = "fullName", expression = "java(mapFullName(response))")
    public abstract Person fromResponseToEntity(PersonResponse response);

    @Mapping(target = "clientStatus", expression = "java(mapClientStatusString(entity))")
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormatString(entity))")
    @Mapping(target = "clientType", expression = "java(mapClientTypeString(entity))")
    public abstract PersonResponse fromEntityToResponse(Person entity);


    ClientType mapClientType(PersonResponse response) {
        return Arrays.stream(ClientType.values())
                .filter(value -> value.getTittle().equals(response.getClientType()))
                .findFirst()
                .orElse(null);
    }

    String mapClientTypeString(Person entity) {
        return entity.getClientType().getTittle();
    }

    Gender mapGender(PersonResponse response) {
        return Arrays.stream(Gender.values())
                .filter(value -> value.getTittle().equals(response.getGender()))
                .findFirst()
                .orElse(null);
    }

    String mapCLientTypeString(Person entity) {
        return entity.getClientType().getTittle();
    }

    String mapClientStatusString(Person entity) {
        return entity.getClientStatus() != null ? entity.getClientStatus().getTittle() : null;
    }

    ClientStatus mapClientStatus(PersonResponse response) throws NoSuchElementException {
        return Arrays.stream(ClientStatus.values())
                .filter(status -> status.getTittle().equals(response.getClientStatus()))
                .findFirst()
                .orElse(null);
    }

    String mapMeetingFormatString(Person entity) {
        return entity.getMeetingFormat() != null ? entity.getMeetingFormat().getTittle() : null;
    }

    MeetingFormat mapMeetingFormat(PersonResponse response) {
        return Arrays.stream(MeetingFormat.values())
                .filter(status -> status.getTittle().equals(response.getMeetingFormat()))
                .findFirst()
                .orElse(null);
    }

    String mapFullName(PersonResponse response) {
        return response.getLastName() + response.getFirstName() + response.getSecondName();
    }
}
