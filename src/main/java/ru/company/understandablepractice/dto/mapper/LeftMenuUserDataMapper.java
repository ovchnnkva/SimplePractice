package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.leftmenu.LeftMenuUserDataResponse;
import ru.company.understandablepractice.model.User;

import java.util.Base64;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class LeftMenuUserDataMapper {

    @Mapping(target = "userPicture", expression = "java(mapUserPicture(user))")
    @Mapping(target = "userName", expression = "java(mapUserName(user))")
    @Mapping(target = "userMail", expression = "java(mapUserMail(user))")
    public abstract LeftMenuUserDataResponse fromEntityToResponse(User user);

    String mapUserPicture(User user) {
        return user.getUserImage() != null ? user.getUserImage() : "";
    }

    String mapUserName(User user){
        return user.getFirstName();
    }

    String mapUserMail(User user){
        return user.getMail();
    }
}
