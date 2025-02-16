package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.customers.CustomerApplicationDto;
import ru.company.understandablepractice.dto.customers.CustomerResponse;
import ru.company.understandablepractice.dto.converter.YesNoConverter;
import ru.company.understandablepractice.dto.customers.PairResponse;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.Pair;
import ru.company.understandablepractice.model.User;
import ru.company.understandablepractice.model.types.*;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Mapper(componentModel = "spring")
public abstract class CustomerMapper {
    @Autowired
    YesNoConverter yesNoConverter;

    @Autowired
    UserMapper userMapper;

    @Mapping(target = "customerCredentials", ignore = true)
    @Mapping(target = "applicationFormToken", ignore = true)
    @Mapping(target = "applicationFormStatus", ignore = true)
    @Mapping(target = "clientType", expression = "java(mapClientType(response))")
    @Mapping(target = "contactMethod", expression = "java(mapContactMethod(response))")
    @Mapping(target = "onlinePlatform", expression = "java(mapOnlinePlatform(response))")
    @Mapping(target = "familyStatus", expression = "java(mapFamilyStatus(response))")
    @Mapping(target = "priorityCommunicationChannel", expression = "java(mapPriorityCommunicationChannel(response))")
    @Mapping(target = "supervisionStatusThisClient", expression = "java(yesNoConverter.stringToBoolean(response.getSupervisionStatusThisClient()))")
    @Mapping(target = "gender", expression = "java(mapGender(response))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatus(response))")
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormat(response))")
    @Mapping(target = "fullName", expression = "java(mapFullName(response))")
    public abstract Customer fromResponseToEntity(CustomerResponse response);

    @Mapping(target = "clientType", expression = "java(mapClientTypeString(customer))")
    @Mapping(target = "contactMethod", expression = "java(mapContactMethodString(customer))")
    @Mapping(target = "onlinePlatform", expression = "java(mapOnlinePlatformString(customer))")
    @Mapping(target = "familyStatus", expression = "java(mapFamilyStatusString(customer))")
    @Mapping(target = "priorityCommunicationChannel", expression = "java(mapPriorityCommunicationChannelString(customer))")
    @Mapping(target = "supervisionStatusThisClient", expression = "java(yesNoConverter.booleanToString(customer.isSupervisionStatusThisClient()))")
    @Mapping(target = "gender", expression = "java(mapGenderString(customer))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatusString(customer))")
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormatString(customer))")
    public abstract CustomerResponse fromEntityToResponse(Customer customer);

    @Mapping(target = "clientType", expression = "java(mapClientTypeString(customer))")
    @Mapping(target = "contactMethod", expression = "java(mapContactMethodString(customer))")
    @Mapping(target = "onlinePlatform", expression = "java(mapOnlinePlatformString(customer))")
    @Mapping(target = "familyStatus", expression = "java(mapFamilyStatusString(customer))")
    @Mapping(target = "priorityCommunicationChannel", expression = "java(mapPriorityCommunicationChannelString(customer))")
    @Mapping(target = "supervisionStatusThisClient", expression = "java(yesNoConverter.booleanToString(customer.isSupervisionStatusThisClient()))")
    @Mapping(target = "gender", expression = "java(mapGenderString(customer))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatusString(customer))")
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormatString(customer))")
    @Mapping(target = "userId", expression = "java(mapUserId(customer))")
    public abstract CustomerApplicationDto fromEntityToApplicationDto(Customer customer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "customerCredentials", ignore = true)
    @Mapping(target = "applicationFormToken", ignore = true)
    @Mapping(target = "applicationFormStatus", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "clientType", expression = "java(mapClientType(response))")
    @Mapping(target = "contactMethod", expression = "java(mapContactMethod(response))")
    @Mapping(target = "onlinePlatform", expression = "java(mapOnlinePlatform(response))")
    @Mapping(target = "familyStatus", expression = "java(mapFamilyStatus(response))")
    @Mapping(target = "priorityCommunicationChannel", expression = "java(mapPriorityCommunicationChannel(response))")
    @Mapping(target = "supervisionStatusThisClient", expression = "java(yesNoConverter.stringToBoolean(response.getSupervisionStatusThisClient()))")
    @Mapping(target = "gender", expression = "java(mapGender(response))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatus(response))", ignore = true)
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormat(response))", ignore = true)
    @Mapping(target = "fullName", expression = "java(mapFullName(response))")
    public abstract void updateEntityFromDto(CustomerResponse response, @MappingTarget Customer entity);

    User mapUser(CustomerApplicationDto dto) {
        return new User(dto.getUserId());
    }

    long mapUserId(Customer customer) {
        return customer.getUser().getId();
    }

    ClientType mapClientType(CustomerResponse response) {
        return Arrays.stream(ClientType.values())
                .filter(value -> value.getTittle().equals(response.getClientType()))
                .findFirst()
                .orElse(null);
    }

    ClientType mapClientType(CustomerApplicationDto response) {
        return Arrays.stream(ClientType.values())
                .filter(value -> value.getTittle().equals(response.getClientType()))
                .findFirst()
                .orElse(null);
    }

    String mapClientTypeString(Customer entity) {
        return entity.getClientType().getTittle();
    }

    Gender mapGender(CustomerResponse response) {
        return Arrays.stream(Gender.values())
                .filter(value -> value.getTittle().equals(response.getGender()))
                .findFirst()
                .orElse(null);
    }

    Gender mapGender(CustomerApplicationDto response) {
        return Arrays.stream(Gender.values())
                .filter(value -> value.getTittle().equals(response.getGender()))
                .findFirst()
                .orElse(null);
    }

    String mapGenderString(Customer entity) {
        return entity.getGender() != null ? entity.getGender().getTittle() : "";
    }

    String mapContactMethodString(Customer customer) {
        return customer.getContactMethod() != null ? customer.getContactMethod().getTittle() : "";
    }

    ContactMethod mapContactMethod(CustomerResponse response) {
        return Arrays.stream(ContactMethod.values())
                .filter(status -> status.getTittle().equals(response.getContactMethod()))
                .findFirst()
                .orElse(null);
    }

    ContactMethod mapContactMethod(CustomerApplicationDto response) {
        return Arrays.stream(ContactMethod.values())
                .filter(status -> status.getTittle().equals(response.getContactMethod()))
                .findFirst()
                .orElse(null);
    }

    String mapOnlinePlatformString(Customer customer) {
        return customer.getOnlinePlatform() != null ? customer.getOnlinePlatform().getTittle() : "";
    }

    OnlinePlatform mapOnlinePlatform(CustomerResponse response) {
        return Arrays.stream(OnlinePlatform.values())
                .filter(status -> status.getTittle().equals(response.getOnlinePlatform()))
                .findFirst()
                .orElse(null);
    }

    OnlinePlatform mapOnlinePlatform(CustomerApplicationDto response) {
        return Arrays.stream(OnlinePlatform.values())
                .filter(status -> status.getTittle().equals(response.getOnlinePlatform()))
                .findFirst()
                .orElse(null);
    }

    String mapFamilyStatusString(Customer customer) {
        return customer.getFamilyStatus() != null ? customer.getFamilyStatus().getTittle() : "";
    }

    FamilyStatus mapFamilyStatus(CustomerResponse response) {
        return Arrays.stream(FamilyStatus.values())
                .filter(status -> status.getTittle().equals(response.getFamilyStatus()))
                .findFirst()
                .orElse(null);
    }

    FamilyStatus mapFamilyStatus(CustomerApplicationDto response) {
        return Arrays.stream(FamilyStatus.values())
                .filter(status -> status.getTittle().equals(response.getFamilyStatus()))
                .findFirst()
                .orElse(null);
    }

    String mapPriorityCommunicationChannelString(Customer customer) {
        return customer.getPriorityCommunicationChannel() != null ? customer.getPriorityCommunicationChannel().getTittle() : "";
    }

    PriorityCommunicationChannel mapPriorityCommunicationChannel(CustomerResponse response) {
        return Arrays.stream(PriorityCommunicationChannel.values())
                .filter(status -> status.getTittle().equals(response.getPriorityCommunicationChannel()))
                .findFirst()
                .orElse(null);
    }

    PriorityCommunicationChannel mapPriorityCommunicationChannel(CustomerApplicationDto response) {
        return Arrays.stream(PriorityCommunicationChannel.values())
                .filter(status -> status.getTittle().equals(response.getPriorityCommunicationChannel()))
                .findFirst()
                .orElse(null);
    }

    String mapClientStatusString(Customer customer) {
        return customer.getClientStatus() != null ? customer.getClientStatus().getTittle() : "";
    }

    ClientStatus mapClientStatus(CustomerResponse response) throws NoSuchElementException {
        return Arrays.stream(ClientStatus.values())
                .filter(status -> status.getTittle().equals(response.getClientStatus()))
                .findFirst()
                .orElse(null);
    }

    ClientStatus mapClientStatus(CustomerApplicationDto response) throws NoSuchElementException {
        return Arrays.stream(ClientStatus.values())
                .filter(status -> status.getTittle().equals(response.getClientStatus()))
                .findFirst()
                .orElse(null);
    }

    String mapMeetingFormatString(Customer customer) {
        return customer.getMeetingFormat() != null ? customer.getMeetingFormat().getTittle() : "";
    }

    MeetingFormat mapMeetingFormat(CustomerResponse response) {
        return Arrays.stream(MeetingFormat.values())
                .filter(status -> status.getTittle().equals(response.getMeetingFormat()))
                .findFirst()
                .orElse(null);
    }

    MeetingFormat mapMeetingFormat(CustomerApplicationDto response) {
        return Arrays.stream(MeetingFormat.values())
                .filter(status -> status.getTittle().equals(response.getMeetingFormat()))
                .findFirst()
                .orElse(null);
    }

    String mapFullName(CustomerResponse response) {
        return String.format("%s %s %s", response.getLastName(), response.getFirstName(), response.getSecondName());
    }

    String mapFullName(CustomerApplicationDto response) {
        return String.format("%s %s %s", response.getLastName(), response.getFirstName(), response.getSecondName());
    }

}
