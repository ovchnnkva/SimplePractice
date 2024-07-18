package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.CustomerResponse;
import ru.company.understandablepractice.dto.MeetResponse;
import ru.company.understandablepractice.dto.PersonResponse;
import ru.company.understandablepractice.dto.UserResponse;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.Meet;
import ru.company.understandablepractice.model.User;
import ru.company.understandablepractice.model.types.MeetingFormat;
import ru.company.understandablepractice.model.types.PaymentType;

import java.util.Arrays;

@Mapper(componentModel = "spring")
public abstract class MeetMapper {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Mapping(target = "customer", expression = "java(mapCustomer(response))")
    @Mapping(target = "user", expression = "java(mapUser(response))")
    @Mapping(target = "formatMeet", expression = "java(mapMeetingFormat(response))")
    @Mapping(target = "paymentType", expression = "java(mapPaymentType(response))")
    public abstract Meet fromResponseToEntity(MeetResponse response);

    @Mapping(target = "customer", expression = "java(mapCustomerResponse(entity))")
    @Mapping(target = "user", expression = "java(mapUserResponse(entity))")
    @Mapping(target = "formatMeet", expression = "java(mapMeetingFormatString(entity))")
    @Mapping(target = "paymentType", expression = "java(mapPaymentTypeString(entity))")
    public abstract MeetResponse fromEntityToResponse(Meet entity);

    Customer mapCustomer(MeetResponse response) {
        return customerMapper.fromResponseToEntity(response.getCustomer());
    }

    CustomerResponse mapCustomerResponse(Meet entity) {
        return customerMapper.fromEntityToResponse(entity.getCustomer());
    }

    User mapUser(MeetResponse response) {
        return new User(response.getUser().getId());
    }

    UserResponse mapUserResponse(Meet entity) {
        return userMapper.fromEntityToResponse(entity.getUser());
    }

    MeetingFormat mapMeetingFormat(MeetResponse response) {
        return Arrays.stream(MeetingFormat.values())
                .filter(value -> value.getTittle().equals(response.getFormatMeet()))
                .findFirst()
                .orElseThrow();
    }

    String mapMeetingFormatString(Meet entity) {
        return entity.getFormatMeet().getTittle();
    }

    PaymentType mapPaymentType(MeetResponse response) {
        return Arrays.stream(PaymentType.values())
                .filter(value -> value.getTitle().equals(response.getPaymentType()))
                .findFirst()
                .orElseThrow();
    }

    String mapPaymentTypeString(Meet entity) {
        return entity.getPaymentType().getTitle();
    }
}