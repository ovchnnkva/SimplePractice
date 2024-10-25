package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.MeetResponse;
import ru.company.understandablepractice.dto.projectivemethod.ProjectiveMethodResponse;
import ru.company.understandablepractice.dto.projectivemethod.TypeMethodResponse;
import ru.company.understandablepractice.model.Meet;
import ru.company.understandablepractice.model.ProjectiveMethod;
import ru.company.understandablepractice.model.TypeMethod;

@Mapper(componentModel = "spring")
public abstract class ProjectiveMethodMapper {

    @Autowired
    private MeetMapper meetMapper;

    @Autowired
    private TypeMethodMapper typeMethodMapper;

    @Mapping(target = "meet", expression = "java(mapMeet(response))")
    @Mapping(target = "typeMethod", expression = "java(mapTypeMethod(response))")
    public abstract ProjectiveMethod fromResponseToEntity(ProjectiveMethodResponse response);

    @Mapping(target = "meet", expression = "java(mapMeetResponse(entity))")
    @Mapping(target = "typeMethod", expression = "java(mapTypeMethodResponse(entity))")
    public abstract ProjectiveMethodResponse fromEntityToResponse(ProjectiveMethod entity);

    Meet mapMeet(ProjectiveMethodResponse response) {
        return meetMapper.fromResponseToEntity(response.getMeet());
    }

    MeetResponse mapMeetResponse(ProjectiveMethod entity) {
        return meetMapper.fromEntityToResponse(entity.getMeet());
    }

    TypeMethod mapTypeMethod(ProjectiveMethodResponse response) {
        return typeMethodMapper.fromResponseToEntity(response.getTypeMethod());
    }

    TypeMethodResponse mapTypeMethodResponse(ProjectiveMethod entity) {
        return typeMethodMapper.fromEntityToResponse(entity.getTypeMethod());
    }
}
