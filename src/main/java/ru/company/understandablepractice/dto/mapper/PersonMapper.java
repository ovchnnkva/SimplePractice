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

    @Mapping(target = "clientType", expression = "java(mapClientType(response))")
    @Mapping(target = "gender", expression = "java(mapGender(response))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatus(response))")
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormat(response))")
    public abstract Person fromResponseToEntity(PersonResponse response);

    @Mapping(target = "clientStatus", expression = "java(mapClientStatusString(entity))")
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormatString(entity))")
    public abstract PersonResponse fromEntityToResponse(Person entity);

    ClientType mapClientType(PersonResponse response) {
        return Arrays.stream(ClientType.values())
                .filter(value -> value.getTittle().equals(response.getClientType()))
                .findFirst()
                .orElse(null);
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
        return entity.getClientStatus().getTittle();
    }

    ClientStatus mapClientStatus(PersonResponse response) throws NoSuchElementException {
        return Arrays.stream(ClientStatus.values())
                .filter(status -> status.getTittle().equals(response.getClientStatus()))
                .findFirst()
                .orElse(null);
    }

    String mapMeetingFormatString(Person entity) {
        return entity.getMeetingFormat().getTittle();
    }

    MeetingFormat mapMeetingFormat(PersonResponse response) {
        return Arrays.stream(MeetingFormat.values())
                .filter(status -> status.getTittle().equals(response.getMeetingFormat()))
                .findFirst()
                .orElse(null);
    }
}
