package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.projectivemethod.PhotoProjectiveMethodResponse;
import ru.company.understandablepractice.dto.projectivemethod.ProjectiveMethodResponse;
import ru.company.understandablepractice.model.PhotoProjectiveMethod;
import ru.company.understandablepractice.model.ProjectiveMethod;

@Mapper(componentModel = "spring")
public abstract class PhotoProjectiveMethodMapper {

    @Autowired
    private ProjectiveMethodMapper projectiveMethodMapper;

    @Mapping(target = "projectiveMethod", expression = "java(mapProjectiveMethod(response))")
    public abstract PhotoProjectiveMethod fromResponseToEntity(PhotoProjectiveMethodResponse response);

    @Mapping(target = "projectiveMethod", expression = "java(mapProjectiveMethodResponse(entity))")
    public abstract PhotoProjectiveMethodResponse fromEntityToResponse(PhotoProjectiveMethod entity);

    ProjectiveMethod mapProjectiveMethod(PhotoProjectiveMethodResponse response) {
        return projectiveMethodMapper.fromResponseToEntity(response.getProjectiveMethod());
    }

    ProjectiveMethodResponse mapProjectiveMethodResponse(PhotoProjectiveMethod entity) {
        return projectiveMethodMapper.fromEntityToResponse(entity.getProjectiveMethod());
    }
}
