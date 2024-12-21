package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.converter.YesNoConverter;
import ru.company.understandablepractice.dto.customers.ChildApplicationDto;
import ru.company.understandablepractice.dto.customers.ChildResponse;
import ru.company.understandablepractice.dto.PersonResponse;
import ru.company.understandablepractice.model.Child;
import ru.company.understandablepractice.model.Person;
import ru.company.understandablepractice.model.User;
import ru.company.understandablepractice.model.types.*;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Mapper(componentModel = "spring")
public abstract class ChildMapper {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    YesNoConverter yesNoConverter;

    @Mapping(target = "clientType", expression = "java(mapClientType(response))")
    @Mapping(target = "bringsClient", expression = "java(mapBringsClient(response))")
    @Mapping(target = "firstParent", expression = "java(mapFirstParent(response))")
    @Mapping(target = "secondParent", expression = "java(mapSecondParent(response))")
    @Mapping(target = "gender", expression = "java(mapGender(response))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatus(response))")
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormat(response))")
    @Mapping(target = "fullName", expression = "java(mapFullName(response))")
    public abstract Child fromResponseToEntity(ChildResponse response);


    @Mapping(target = "bringsClient", expression = "java(mapBringsClientString(child))")
    @Mapping(target = "firstParent", expression = "java(mapFirstParentResponse(child))")
    @Mapping(target = "secondParent", expression = "java(mapSecondParentResponse(child))")
    @Mapping(target = "gender", expression = "java(mapGenderString(child))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatusString(child))")
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormatString(child))")
    @Mapping(target = "supervisionStatusThisClient",
            expression = "java(yesNoConverter.stringToBoolean(response.getSubscriptionActive()))")
    public abstract ChildResponse fromEntityToResponse(Child child);

    @Mapping(target = "clientType", expression = "java(mapClientType(dto))")
    @Mapping(target = "bringsClient", expression = "java(mapBringsClient(dto))")
    @Mapping(target = "firstParent", expression = "java(mapFirstParent(dto))")
    @Mapping(target = "secondParent", expression = "java(mapSecondParent(dto))")
    @Mapping(target = "gender", expression = "java(mapGender(dto))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatus(dto))")
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormat(dto))")
    @Mapping(target = "fullName", expression = "java(mapFullName(dto))")
    @Mapping(target = "user", expression = "java(mapUser(dto))")
    public abstract Child fromApplicationDtoToEntity(ChildApplicationDto dto);

    @Mapping(target = "bringsClient", expression = "java(mapBringsClientString(child))")
    @Mapping(target = "firstParent", expression = "java(mapFirstParentResponse(child))")
    @Mapping(target = "secondParent", expression = "java(mapSecondParentResponse(child))")
    @Mapping(target = "gender", expression = "java(mapGenderString(child))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatusString(child))")
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormatString(child))")
    @Mapping(target = "userId", expression = "java(mapUserId(child))")
    public abstract ChildApplicationDto fromEntityToApplicationDto(Child child);

    User mapUser(ChildApplicationDto dto) {
        return new User(dto.getUserId());
    }

    long mapUserId(Child child) {
        return child.getUser().getId();
    }

    ClientType mapClientType(ChildResponse response) {
        return Arrays.stream(ClientType.values())
                .filter(value -> value.getTittle().equals(response.getClientType()))
                .findFirst()
                .orElse(null);
    }

    ClientType mapClientType(ChildApplicationDto response) {
        return Arrays.stream(ClientType.values())
                .filter(value -> value.getTittle().equals(response.getClientType()))
                .findFirst()
                .orElse(null);
    }

    String mapCLientTypeString(Child entity) {
        return entity.getClientType() != null ? entity.getClientType().getTittle() : "";
    }

    String mapBringsClientString(Child child){
        return child.getBringsClient() != null ? child.getBringsClient().getTitle() : "";
    }

    BringsClient mapBringsClient(ChildResponse response) throws NoSuchElementException{
        return Arrays.stream(BringsClient.values())
                .filter(bringsClient -> bringsClient.getTitle().equals(response.getBringsClient()))
                .findFirst()
                .orElse(null);
    }

    BringsClient mapBringsClient(ChildApplicationDto response) throws NoSuchElementException{
        return Arrays.stream(BringsClient.values())
                .filter(bringsClient -> bringsClient.getTitle().equals(response.getBringsClient()))
                .findFirst()
                .orElse(null);
    }

    PersonResponse mapFirstParentResponse(Child child){
        return personMapper.fromEntityToResponse(child.getFirstParent());
    }

    Person mapFirstParent(ChildResponse response){
        return personMapper.fromResponseToEntity(response.getFirstParent());
    }

    Person mapFirstParent(ChildApplicationDto response){
        return personMapper.fromResponseToEntity(response.getFirstParent());
    }

    PersonResponse mapSecondParentResponse(Child child){
        return personMapper.fromEntityToResponse(child.getSecondParent());
    }

    Person mapSecondParent(ChildResponse response){
        return personMapper.fromResponseToEntity(response.getSecondParent());
    }

    Person mapSecondParent(ChildApplicationDto response){
        return personMapper.fromResponseToEntity(response.getSecondParent());
    }

    Gender mapGender(ChildResponse response) {
        return Arrays.stream(Gender.values())
                .filter(value -> value.getTittle().equals(response.getGender()))
                .findFirst()
                .orElse(null);
    }

    Gender mapGender(ChildApplicationDto response) {
        return Arrays.stream(Gender.values())
                .filter(value -> value.getTittle().equals(response.getGender()))
                .findFirst()
                .orElse(null);
    }

    String mapGenderString(Child entity) {
        return entity.getGender() != null ? entity.getGender().getTittle() : "";
    }

    String mapClientStatusString(Child child) {
        return child.getClientStatus() != null ? child.getClientStatus().getTittle() : "";
    }

    ClientStatus mapClientStatus(ChildResponse response) throws NoSuchElementException {
        return Arrays.stream(ClientStatus.values())
                .filter(status -> status.getTittle().equals(response.getClientStatus()))
                .findFirst()
                .orElse(null);
    }

    ClientStatus mapClientStatus(ChildApplicationDto response) throws NoSuchElementException {
        return Arrays.stream(ClientStatus.values())
                .filter(status -> status.getTittle().equals(response.getClientStatus()))
                .findFirst()
                .orElse(null);
    }

    String mapMeetingFormatString(Child child) {
        return child.getMeetingFormat() != null ? child.getMeetingFormat().getTittle() : "";
    }

    MeetingFormat mapMeetingFormat(ChildResponse response) {
        return Arrays.stream(MeetingFormat.values())
                .filter(status -> status.getTittle().equals(response.getMeetingFormat()))
                .findFirst()
                .orElse(null);
    }

    MeetingFormat mapMeetingFormat(ChildApplicationDto response) {
        return Arrays.stream(MeetingFormat.values())
                .filter(status -> status.getTittle().equals(response.getMeetingFormat()))
                .findFirst()
                .orElse(null);
    }

    String mapFullName(ChildResponse response) {
        return String.format("%s %s %s", response.getLastName(), response.getFirstName(), response.getSecondName());
    }

    String mapFullName(ChildApplicationDto response) {
        return String.format("%s %s %s", response.getLastName(), response.getFirstName(), response.getSecondName());
    }

    String mapSupervisionStatusThisClient(Child entity) {
        return entity.isSupervisionStatusThisClient() ? "Да" : "Нет";
    }
}
