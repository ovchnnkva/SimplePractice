package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.ChildResponse;
import ru.company.understandablepractice.dto.CustomerResponse;
import ru.company.understandablepractice.dto.UserResponse;
import ru.company.understandablepractice.model.Child;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.User;
import ru.company.understandablepractice.model.types.*;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Mapper(componentModel = "spring")
public abstract class ChildMapper {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private UserMapper userMapper;

    @Mapping(target = "user", expression = "java(mapUser(response))")
    @Mapping(target = "clientType", expression = "java(mapClientType(response))")
    @Mapping(target = "bringsClient", expression = "java(mapBringsClient(response))")
    @Mapping(target = "firstParent", expression = "java(mapFirstParent(response))")
    @Mapping(target = "secondParent", expression = "java(mapSecondParent(response))")
    @Mapping(target = "gender", expression = "java(mapGender(response))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatus(response))")
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormat(response))")
    public abstract Child fromResponseToEntity(ChildResponse response);

    @Mapping(target = "user", expression = "java(mapUserResponse(child))")
    @Mapping(target = "bringsClient", expression = "java(mapBringsClientString(child))")
    @Mapping(target = "firstParent", expression = "java(mapFirstParentResponse(child))")
    @Mapping(target = "secondParent", expression = "java(mapSecondParentResponse(child))")
    @Mapping(target = "gender", expression = "java(mapGenderString(child))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatusString(child))")
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormatString(child))")
    public abstract ChildResponse fromEntityToResponse(Child child);

    User mapUser(ChildResponse response) {
        return userMapper.fromResponseToEntity(response.getUser());
    }

    UserResponse mapUserResponse(Child customer) {
        return userMapper.fromEntityToResponse(customer.getUser());
    }

    ClientType mapClientType(ChildResponse response) {
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

    CustomerResponse mapFirstParentResponse(Child child){
        return customerMapper.fromEntityToResponse(child.getFirstParent());
    }

    Customer mapFirstParent(ChildResponse response){
        return customerMapper.fromResponseToEntity(response.getFirstParent());
    }

    CustomerResponse mapSecondParentResponse(Child child){
        return customerMapper.fromEntityToResponse(child.getSecondParent());
    }

    Customer mapSecondParent(ChildResponse response){
        return customerMapper.fromResponseToEntity(response.getSecondParent());
    }

    Gender mapGender(ChildResponse response) {
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

    String mapMeetingFormatString(Child child) {
        return child.getMeetingFormat() != null ? child.getMeetingFormat().getTittle() : "";
    }

    MeetingFormat mapMeetingFormat(ChildResponse response) {
        return Arrays.stream(MeetingFormat.values())
                .filter(status -> status.getTittle().equals(response.getMeetingFormat()))
                .findFirst()
                .orElse(null);
    }
}
