package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.converter.YesNoConverter;
import ru.company.understandablepractice.dto.customers.PairApplicationDto;
import ru.company.understandablepractice.dto.customers.PairResponse;
import ru.company.understandablepractice.dto.PersonResponse;
import ru.company.understandablepractice.model.*;
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

    @Autowired
    YesNoConverter yesNoConverter;

    @Mapping(target = "clientType", expression = "java(mapClientType(response))")
    @Mapping(target = "contactMethod", expression = "java(mapContactMethod(response))")
    @Mapping(target = "onlinePlatform", expression = "java(mapOnlinePlatform(response))")
    @Mapping(target = "familyStatus", expression = "java(mapFamilyStatus(response))")
    @Mapping(target = "secondPerson", expression = "java(mapSecondPerson(response))")
    @Mapping(target = "gender", expression = "java(mapGender(response))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatus(response))")
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormat(response))")
    @Mapping(target = "fullName", expression = "java(mapFullName(response))")
    @Mapping(target = "priorityCommunicationChannel", expression = "java(mapPriorityCommunicationChannel(response))")
    public abstract Pair fromResponseToEntity(PairResponse response);

    @Mapping(target = "clientType", expression = "java(mapClientTypeString(entity))")
    @Mapping(target = "contactMethod", expression = "java(mapContactMethodString(entity))")
    @Mapping(target = "onlinePlatform", expression = "java(mapOnlinePlatformString(entity))")
    @Mapping(target = "familyStatus", expression = "java(mapFamilyStatusString(entity))")
    @Mapping(target = "secondPerson", expression = "java(mapSecondPersonResponse(entity))")
    @Mapping(target = "gender", expression = "java(mapGenderString(entity))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatusString(entity))")
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormatString(entity))")
    @Mapping(target = "supervisionStatusThisClient",
            expression = "java(yesNoConverter.booleanToString(entity.isSupervisionStatusThisClient()))")
    @Mapping(target = "priorityCommunicationChannel", expression = "java(mapPriorityCommunicationChannelString(entity))")
    public abstract PairResponse fromEntityToResponse(Pair entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "clientType", expression = "java(mapClientType(response))")
    @Mapping(target = "contactMethod", expression = "java(mapContactMethod(response))")
    @Mapping(target = "onlinePlatform", expression = "java(mapOnlinePlatform(response))")
    @Mapping(target = "familyStatus", expression = "java(mapFamilyStatus(response))")
    @Mapping(target = "secondPerson", expression = "java(updateSecondPerson(response, entity))")
    @Mapping(target = "gender", expression = "java(mapGender(response))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatus(response))", ignore = true)
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormat(response))", ignore = true)
    @Mapping(target = "fullName", expression = "java(mapFullName(response))")
    @Mapping(target = "priorityCommunicationChannel", expression = "java(mapPriorityCommunicationChannel(response))")
    @Mapping(target = "supervisionStatusThisClient", expression = "java(yesNoConverter.stringToBoolean(response.getSupervisionStatusThisClient()))")
    public abstract void updateEntityFromDto(PairResponse response, @MappingTarget Pair entity);

    User mapUser(PairApplicationDto dto) {
        return new User(dto.getUserId());
    }

    long mapUserId(Pair entity) {
        return entity.getUser().getId();
    }

    Person mapSecondPerson(PairResponse response) {
        return personMapper.fromResponseToEntity(response.getSecondPerson());
    }

    Person mapSecondPerson(PairApplicationDto response) {
        return personMapper.fromResponseToEntity(response.getSecondPerson());
    }

    PersonResponse mapSecondPersonResponse(Pair entity) {
        return personMapper.fromEntityToResponse(entity.getSecondPerson());
    }

    Person updateSecondPerson(PairResponse response, Pair entity) {
        personMapper.updateEntityFromDto(response.getSecondPerson(), entity.getSecondPerson());
        return entity.getSecondPerson();
    }

    ContactMethod mapContactMethod(PairResponse response){
        return Arrays.stream(ContactMethod.values())
                .filter(value -> value.getTittle().equals(response.getContactMethod()))
                .findFirst()
                .orElse(null);
    }

    ContactMethod mapContactMethod(PairApplicationDto response){
        return Arrays.stream(ContactMethod.values())
                .filter(value -> value.getTittle().equals(response.getContactMethod()))
                .findFirst()
                .orElse(null);
    }

    String mapContactMethodString(Pair entity) {
        return entity.getContactMethod() != null ? entity.getContactMethod().getTittle() : "";
    }

    OnlinePlatform mapOnlinePlatform(PairResponse response) {
        return Arrays.stream(OnlinePlatform.values())
                .filter(value -> value.getTittle().equals(response.getOnlinePlatform()))
                .findFirst()
                .orElse(null);
    }

    OnlinePlatform mapOnlinePlatform(PairApplicationDto response) {
        return Arrays.stream(OnlinePlatform.values())
                .filter(value -> value.getTittle().equals(response.getOnlinePlatform()))
                .findFirst()
                .orElse(null);
    }

    String mapOnlinePlatformString(Pair entity) {
        return entity.getOnlinePlatform() != null ? entity.getOnlinePlatform().getTittle() : "";
    }

    ClientType mapClientType(PairResponse response) {
        return Arrays.stream(ClientType.values())
                .filter(value -> value.getTittle().equals(response.getClientType()))
                .findFirst()
                .orElse(null);
    }

    ClientType mapClientType(PairApplicationDto response) {
        return Arrays.stream(ClientType.values())
                .filter(value -> value.getTittle().equals(response.getClientType()))
                .findFirst()
                .orElse(null);
    }

    String mapClientTypeString(Pair entity) {
        return entity.getClientType() != null ? entity.getClientType().getTittle() : "";
    }

    Gender mapGender(PairResponse response) {
        return Arrays.stream(Gender.values())
                .filter(value -> value.getTittle().equals(response.getGender()))
                .findFirst()
                .orElse(null);
    }

    Gender mapGender(PairApplicationDto response) {
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

    FamilyStatus mapFamilyStatus(PairApplicationDto response) {
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

    ClientStatus mapClientStatus(PairApplicationDto response) throws NoSuchElementException {
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

    MeetingFormat mapMeetingFormat(PairApplicationDto response) {
        return Arrays.stream(MeetingFormat.values())
                .filter(status -> status.getTittle().equals(response.getMeetingFormat()))
                .findFirst()
                .orElse(null);
    }

    String mapFullName(PairResponse response) {
        return String.format("%s %s %s", response.getLastName(), response.getFirstName(), response.getSecondName());
    }

    String mapFullName(PairApplicationDto response) {
        return String.format("%s %s %s", response.getLastName(), response.getFirstName(), response.getSecondName());
    }

    PriorityCommunicationChannel mapPriorityCommunicationChannel(PairResponse response) {
        return Arrays.stream(PriorityCommunicationChannel.values())
                .filter(status -> status.getTittle().equals(response.getPriorityCommunicationChannel()))
                .findFirst()
                .orElse(null);
    }

    PriorityCommunicationChannel mapPriorityCommunicationChannel(PairApplicationDto response) {
        return Arrays.stream(PriorityCommunicationChannel.values())
                .filter(status -> status.getTittle().equals(response.getPriorityCommunicationChannel()))
                .findFirst()
                .orElse(null);
    }

    String mapPriorityCommunicationChannelString(Pair pair) {
        return pair.getPriorityCommunicationChannel() != null ? pair.getPriorityCommunicationChannel().getTittle() : "";
    }
}
