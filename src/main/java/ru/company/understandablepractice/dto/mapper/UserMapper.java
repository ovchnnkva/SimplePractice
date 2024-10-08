package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.UserResponse;
import ru.company.understandablepractice.dto.converter.YesNoConverter;
import ru.company.understandablepractice.model.User;


@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Autowired
    protected YesNoConverter yesNoConverter;

    @Mapping(target = "subscriptionActive",
            expression = "java(yesNoConverter.stringToBoolean(response.getSubscriptionActive()))")
    public abstract User fromResponseToEntity(UserResponse response);

    @Mapping(target = "subscriptionActive",
            expression = "java(yesNoConverter.booleanToString(user.isSubscriptionActive()))")
    public abstract UserResponse fromEntityToResponse(User user);

}
