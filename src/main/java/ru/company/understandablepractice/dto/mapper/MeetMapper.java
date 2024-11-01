package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.MeetResponse;
import ru.company.understandablepractice.dto.PersonResponse;
import ru.company.understandablepractice.dto.UserResponse;
import ru.company.understandablepractice.model.Meet;
import ru.company.understandablepractice.model.Person;
import ru.company.understandablepractice.model.User;
import ru.company.understandablepractice.model.types.MeetingFormat;
import ru.company.understandablepractice.model.types.PaymentType;

import java.util.Arrays;

@Mapper(componentModel = "spring")
public abstract class MeetMapper {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PersonMapper personMapper;

    @Mapping(target = "person", expression = "java(mapPerson(response))")
    @Mapping(target = "formatMeet", expression = "java(mapMeetingFormat(response))")
    @Mapping(target = "paymentType", expression = "java(mapPaymentType(response))")
    public abstract Meet fromResponseToEntity(MeetResponse response);

    @Mapping(target = "person", expression = "java(mapPersonResponse(entity))")
    @Mapping(target = "formatMeet", expression = "java(mapMeetingFormatString(entity))")
    @Mapping(target = "paymentType", expression = "java(mapPaymentTypeString(entity))")
    public abstract MeetResponse fromEntityToResponse(Meet entity);

    Person mapPerson(MeetResponse response) {
        return response.getPerson() != null ? personMapper.fromResponseToEntity(response.getPerson()) : null;
    }

    PersonResponse mapPersonResponse(Meet entity) {
        return entity.getPerson() != null ? personMapper.fromEntityToResponse(entity.getPerson()) : null;
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
        return entity.getFormatMeet().getTittle();
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
}
