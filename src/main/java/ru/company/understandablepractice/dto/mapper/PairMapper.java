package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.CustomerResponse;
import ru.company.understandablepractice.dto.PairResponse;
import ru.company.understandablepractice.dto.PersonResponse;
import ru.company.understandablepractice.dto.UserResponse;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.Pair;
import ru.company.understandablepractice.model.Person;
import ru.company.understandablepractice.model.User;
import ru.company.understandablepractice.model.types.*;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Mapper(componentModel = "spring")
public abstract class PairMapper {
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private UserMapper userMapper;

    @Mapping(target = "clientType", expression = "java(mapClientType(response))")
    @Mapping(target = "familyStatus", expression = "java(mapFamilyStatus(response))")
    @Mapping(target = "secondPerson", expression = "java(mapSecondPerson(response))")
    @Mapping(target = "gender", expression = "java(mapGender(response))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatus(response))")
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormat(response))")
    public abstract Pair fromResponseToEntity(PairResponse response);

    @Mapping(target = "familyStatus", expression = "java(mapFamilyStatusString(entity))")
    @Mapping(target = "secondPerson", expression = "java(mapSecondPersonResponse(entity))")
    @Mapping(target = "gender", expression = "java(mapGenderString(entity))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatusString(entity))")
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormatString(entity))")
    public abstract PairResponse fromEntityToResponse(Pair entity);

    Person mapSecondPerson(PairResponse response) {
        return personMapper.fromResponseToEntity(response.getSecondPerson());
    }

    PersonResponse mapSecondPersonResponse(Pair entity) {
        return personMapper.fromEntityToResponse(entity.getSecondPerson());
    }

    ClientType mapClientType(PairResponse response) {
        return Arrays.stream(ClientType.values())
                .filter(value -> value.getTittle().equals(response.getClientType()))
                .findFirst()
                .orElse(null);
    }

    String mapCLientTypeString(Pair entity) {
        return entity.getClientType() != null ? entity.getClientType().getTittle() : "";
    }

    Gender mapGender(PairResponse response) {
        return Arrays.stream(Gender.values())
                .filter(value -> value.getTittle().equals(response.getGender()))
                .findFirst()
                .orElse(null);
    }

    String mapGenderString(Pair entity) {
        return entity.getGender() != null ? entity.getGender().getTittle() : "";
    }

    String mapFamilyStatusString(Pair entity) {
        return entity.getClientType() != null ? entity.getClientType().getTittle() : "";
    }

    FamilyStatus mapFamilyStatus(PairResponse response) {
        return Arrays.stream(FamilyStatus.values())
                .filter(value -> value.getTittle().equals(response.getFamilyStatus()))
                .findFirst()
                .orElse(null);
    }

    String mapClientStatusString(Pair entity) {
        return entity.getClientStatus() != null ? entity.getClientStatus().getTittle() : "";
    }

    ClientStatus mapClientStatus(PairResponse response) throws NoSuchElementException {
        return Arrays.stream(ClientStatus.values())
                .filter(status -> status.getTittle().equals(response.getClientStatus()))
                .findFirst()
                .orElse(null);
    }

    String mapMeetingFormatString(Pair entity) {
        return entity.getMeetingFormat() != null ? entity.getMeetingFormat().getTittle() : "";
    }

    MeetingFormat mapMeetingFormat(PairResponse response) {
        return Arrays.stream(MeetingFormat.values())
                .filter(status -> status.getTittle().equals(response.getMeetingFormat()))
                .findFirst()
                .orElse(null);
    }
}
