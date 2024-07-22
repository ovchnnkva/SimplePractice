package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.leftmenu.LeftMenuUserDataResponse;
import ru.company.understandablepractice.model.User;

@Mapper(componentModel = "spring")
public abstract class LeftMenuUserDataMapper {

    @Mapping(target = "userPicture", expression = "java(mapUserPicture(user))")
    @Mapping(target = "userName", expression = "java(mapUserName(user))")
    @Mapping(target = "userMail", expression = "java(mapUserMail(user))")
    public abstract LeftMenuUserDataResponse fromEntityToResponse(User user);

    //TODO Добавить пользователю картинку и реализовать ее маппинг
    String mapUserPicture(User user) {
        return "picture";
    }

    String mapUserName(User user){
        return user.getFullName();
    }

    String mapUserMail(User user){
        return user.getMail();
    }
}
