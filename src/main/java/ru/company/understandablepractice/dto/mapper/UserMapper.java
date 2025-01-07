package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.MeetResponse;
import ru.company.understandablepractice.dto.UserResponse;
import ru.company.understandablepractice.dto.converter.YesNoConverter;
import ru.company.understandablepractice.model.Meet;
import ru.company.understandablepractice.model.User;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;


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

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "subscriptionActive",
            expression = "java(yesNoConverter.stringToBoolean(response.getSubscriptionActive()))")
    public abstract void updateEntityFromDto(UserResponse response, @MappingTarget User entity);

    String mapUserImageToString(User entity){
        return new String(Base64.getDecoder().decode(Optional.ofNullable(entity.getUserImage()).orElse("")));
    }

    String mapUserImageToBase64String(UserResponse response){
        return response.getUserImage() != null ? response.getUserImage() : "";
    }
}
