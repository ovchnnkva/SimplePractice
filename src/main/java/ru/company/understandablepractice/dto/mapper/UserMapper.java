package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.UserResponse;
import ru.company.understandablepractice.dto.converter.YesNoConverter;
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
    @Mapping(target = "userImage", expression = "java(mapUserImageToBase64String(response))")
    public abstract User fromResponseToEntity(UserResponse response);

    @Mapping(target = "subscriptionActive",
            expression = "java(yesNoConverter.booleanToString(user.isSubscriptionActive()))")
    @Mapping(target = "userImage", expression = "java(mapUserImageToString(user))")
    public abstract UserResponse fromEntityToResponse(User user);

    String mapUserImageToString(User entity){
        return new String(Base64.getDecoder().decode(Optional.ofNullable(entity.getUserImage()).orElse("")));
    }

    String mapUserImageToBase64String(UserResponse response){
        if (response.getUserImage() != null){
            return Base64.getEncoder().encodeToString(response.getUserImage().getBytes(StandardCharsets.UTF_8));
        } else {
            return "";
        }
    }
}
