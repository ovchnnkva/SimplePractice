package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.CustomerResponse;
import ru.company.understandablepractice.dto.UserResponse;
import ru.company.understandablepractice.dto.converter.YesNoConverter;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.User;
import ru.company.understandablepractice.model.types.*;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public abstract class CustomerMapper {
    @Autowired
    private YesNoConverter yesNoConverter;

    @Autowired
    private UserMapper userMapper;

    @Mapping(target = "user", expression = "java(mapUser(response))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatus(response))")
    @Mapping(target = "contactMethod", expression = "java(mapContactMethod(response))")
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormat(response))")
    @Mapping(target = "onlinePlatform", expression = "java(mapOnlinePlatform(response))")
    @Mapping(target = "familyStatus", expression = "java(mapFamilyStatus(response))")
    @Mapping(target = "priorityCommunicationChannel", expression = "java(mapPriorityCommunicationChannel(response))")
    @Mapping(target = "supervisionStatusThisClient", expression = "java(yesNoConverter.stringToBoolean(response.getSupervisionStatusThisClient()))")
    public abstract Customer fromResponseToEntity(CustomerResponse response);

    @Mapping(target = "user", expression = "java(mapUserResponse(customer))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatusString(customer))")
    @Mapping(target = "contactMethod", expression = "java(mapContactMethodString(customer))")
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormatString(customer))")
    @Mapping(target = "onlinePlatform", expression = "java(mapOnlinePlatformString(customer))")
    @Mapping(target = "familyStatus", expression = "java(mapFamilyStatusString(customer))")
    @Mapping(target = "priorityCommunicationChannel", expression = "java(mapPriorityCommunicationChannelString(customer))")
    @Mapping(target = "supervisionStatusThisClient", expression = "java(yesNoConverter.booleanToString(customer.isSupervisionStatusThisClient()))")
    public abstract CustomerResponse fromEntityToResponse(Customer customer);

    UserResponse mapUserResponse(Customer customer) {
        return userMapper.fromEntityToResponse(customer.getUser());
    }

    User mapUser(CustomerResponse response) {
        return userMapper.fromResponseToEntity(response.getUser());
    }

    String mapClientStatusString(Customer customer) {
        return customer.getClientStatus().getTittle();
    }

    ClientStatus mapClientStatus(CustomerResponse response) throws NoSuchElementException {
        return Arrays.stream(ClientStatus.values())
                .filter(status -> status.getTittle().equals(response.getClientStatus()))
                .findFirst()
                .orElseThrow();
    }

    String mapContactMethodString(Customer customer) {
        return customer.getContactMethod().getTittle();
    }

    ContactMethod mapContactMethod(CustomerResponse response) {
        return Arrays.stream(ContactMethod.values())
                .filter(status -> status.getTittle().equals(response.getClientStatus()))
                .findFirst()
                .orElseThrow();
    }

    String mapMeetingFormatString(Customer customer) {
        return customer.getMeetingFormat().getTittle();
    }

    MeetingFormat mapMeetingFormat(CustomerResponse response) {
        return Arrays.stream(MeetingFormat.values())
                .filter(status -> status.getTittle().equals(response.getClientStatus()))
                .findFirst()
                .orElseThrow();
    }

    String mapOnlinePlatformString(Customer customer) {
        return customer.getOnlinePlatform().getTittle();
    }

    OnlinePlatform mapOnlinePlatfotm(CustomerResponse response) {
        return Arrays.stream(OnlinePlatform.values())
                .filter(status -> status.getTittle().equals(response.getClientStatus()))
                .findFirst()
                .orElseThrow();
    }

    String mapFamilyStatusString(Customer customer) {
        return customer.getFamilyStatus().getTittle();
    }

    FamilyStatus mapFamilyStatus(CustomerResponse response) {
        return Arrays.stream(FamilyStatus.values())
                .filter(status -> status.getTittle().equals(response.getClientStatus()))
                .findFirst()
                .orElseThrow();
    }

    String mapPriorityCommunicationChannelString(Customer customer) {
        return customer.getPriorityCommunicationChannel().getTittle();
    }

    PriorityCommunicationChannel mapPriorityCommunicationChannel(CustomerResponse response) {
        return Arrays.stream(PriorityCommunicationChannel.values())
                .filter(status -> status.getTittle().equals(response.getClientStatus()))
                .findFirst()
                .orElseThrow();
    }
}
