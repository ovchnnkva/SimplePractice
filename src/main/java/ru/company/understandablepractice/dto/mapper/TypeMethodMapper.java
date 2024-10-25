package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.UserResponse;
import ru.company.understandablepractice.dto.projectivemethod.ProjectiveMethodResponse;
import ru.company.understandablepractice.dto.projectivemethod.TypeMethodResponse;
import ru.company.understandablepractice.model.ProjectiveMethod;
import ru.company.understandablepractice.model.TypeMethod;
import ru.company.understandablepractice.model.User;

@Mapper(componentModel = "spring")
public abstract class TypeMethodMapper {

    @Autowired
    private UserMapper userMapper;

    @Mapping(target = "user", expression = "java(mapUser(response))")
    public abstract TypeMethod fromResponseToEntity(TypeMethodResponse response);

    @Mapping(target = "user", expression = "java(mapUserResponse(entity))")
    public abstract TypeMethodResponse fromEntityToResponse(TypeMethod entity);

    User mapUser(TypeMethodResponse response) {
        return userMapper.fromResponseToEntity(response.getUser());
    }

    UserResponse mapUserResponse(TypeMethod entity) {
        return userMapper.fromEntityToResponse(entity.getUser());
    }
}
