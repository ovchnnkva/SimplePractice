package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.customers.CustomerResponse;
import ru.company.understandablepractice.dto.MeetResponse;
import ru.company.understandablepractice.dto.UserResponse;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.Meet;
import ru.company.understandablepractice.model.types.MeetingFormat;
import ru.company.understandablepractice.model.types.PaymentType;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

@Mapper(componentModel = "spring")
public abstract class MeetMapper {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Mapping(target = "customer", expression = "java(mapCustomer(response))")
    @Mapping(target = "formatMeet", expression = "java(mapMeetingFormat(response))")
    @Mapping(target = "paymentType", expression = "java(mapPaymentType(response))")
    @Mapping(target = "startMeet", expression = "java(mapStartTimeLocalTime(response))")
    @Mapping(target = "endMeet", expression = "java(mapEndTimeLocalTime(response))")
    @Mapping(target = "nextStartMeet", expression = "java(mapNextStartTimeLocalTime(response))")
    @Mapping(target = "nextEndMeet", expression = "java(mapNextEndTimeLocalTime(response))")
    public abstract Meet fromResponseToEntity(MeetResponse response);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "customer", expression = "java(mapCustomerResponse(entity))")
    @Mapping(target = "formatMeet", expression = "java(mapMeetingFormatString(entity))")
    @Mapping(target = "paymentType", expression = "java(mapPaymentTypeString(entity))")
    @Mapping(target = "startMeet", expression = "java(mapStartTime(entity))")
    @Mapping(target = "endMeet", expression = "java(mapEndTime(entity))")
    @Mapping(target = "nextStartMeet", expression = "java(mapNextStartTime(entity))")
    @Mapping(target = "nextEndMeet", expression = "java(mapNextEndTime(entity))")
    public abstract MeetResponse fromEntityToResponse(Meet entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "customer", expression = "java(mapCustomer(response))")
    @Mapping(target = "formatMeet", expression = "java(mapMeetingFormat(response))")
    @Mapping(target = "paymentType", expression = "java(mapPaymentType(response))")
    @Mapping(target = "startMeet", expression = "java(mapStartTimeLocalTime(response))")
    @Mapping(target = "endMeet", expression = "java(mapEndTimeLocalTime(response))")
    @Mapping(target = "nextStartMeet", expression = "java(mapNextStartTimeLocalTime(response))")
    @Mapping(target = "nextEndMeet", expression = "java(mapNextEndTimeLocalTime(response))")
    public abstract void updateEntityFromDto(MeetResponse response, @MappingTarget Meet entity);

    Customer mapCustomer(MeetResponse response) {
        return response.getCustomer() != null ? customerMapper.fromResponseToEntity(response.getCustomer()) : null;
    }

    CustomerResponse mapCustomerResponse(Meet entity) {
        return entity.getCustomer() != null ? customerMapper.fromEntityToResponse(entity.getCustomer()) : null;
    }

    UserResponse mapUserResponse(Meet entity) {
        return userMapper.fromEntityToResponse(entity.getUser());
    }

    MeetingFormat mapMeetingFormat(MeetResponse response) {
        return Arrays.stream(MeetingFormat.values())
                .filter(value -> value.getTittle().equals(response.getFormatMeet()))
                .findFirst()
                .orElse(null);
    }

    String mapMeetingFormatString(Meet entity) {
        return entity.getFormatMeet() != null ? entity.getFormatMeet().getTittle() : "";
    }

    PaymentType mapPaymentType(MeetResponse response) {
        return Arrays.stream(PaymentType.values())
                .filter(value -> value.getTitle().equals(response.getPaymentType()))
                .findFirst()
                .orElse(null);
    }

    String mapPaymentTypeString(Meet entity) {
        return entity.getPaymentType() != null ? entity.getPaymentType().getTitle() : "";
    }

    LocalDateTime mapStartTime(Meet entity) {
        return entity.getDateMeet() != null &&  entity.getStartMeet() != null
                ? LocalDateTime.of(entity.getDateMeet(), entity.getStartMeet())
                : null;
    }

    LocalDateTime mapEndTime(Meet entity) {
        return entity.getDateMeet() != null && entity.getEndMeet() != null
                ? LocalDateTime.of(entity.getDateMeet(), entity.getEndMeet())
                : null;
    }

    LocalDateTime mapNextStartTime(Meet entity) {
        return entity.getNextDayMeet() != null && entity.getNextStartMeet() != null
                ? LocalDateTime.of(entity.getNextDayMeet(), entity.getNextStartMeet())
                : null;
    }

    LocalDateTime mapNextEndTime(Meet entity) {
        return entity.getNextDayMeet() != null &&  entity.getNextEndMeet() != null
                ? LocalDateTime.of(entity.getNextDayMeet(), entity.getNextEndMeet())
                : null;
    }

    LocalTime mapStartTimeLocalTime(MeetResponse response) {
        return response.getStartMeet() != null ? response.getStartMeet().toLocalTime() : null;
    }

    LocalTime mapEndTimeLocalTime(MeetResponse response) {
        return response.getEndMeet() != null ? response.getEndMeet().toLocalTime() : null;
    }

    LocalTime mapNextStartTimeLocalTime(MeetResponse response) {
        return response.getNextStartMeet() != null ? response.getNextStartMeet().toLocalTime() : null;
    }

    LocalTime mapNextEndTimeLocalTime(MeetResponse response) {
        return response.getNextEndMeet() != null ? response.getNextEndMeet().toLocalTime() : null;
    }

}
