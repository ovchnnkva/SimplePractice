package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.MeetResponse;
import ru.company.understandablepractice.dto.projectivemethod.ProjectiveMethodResponse;
import ru.company.understandablepractice.model.Meet;
import ru.company.understandablepractice.model.ProjectiveMethod;

@Mapper(componentModel = "spring")
public abstract class ProjectiveMethodMapper {

    @Autowired
    private MeetMapper meetMapper;

    @Mapping(target = "meet", expression = "java(mapMeet(response))")
    public abstract ProjectiveMethod fromResponseToEntity(ProjectiveMethodResponse response);

    @Mapping(target = "meet", expression = "java(mapMeetResponse(entity))")
    public abstract ProjectiveMethodResponse fromEntityToResponse(ProjectiveMethod entity);

    Meet mapMeet(ProjectiveMethodResponse response) {
        return meetMapper.fromResponseToEntity(response.getMeet());
    }

    MeetResponse mapMeetResponse(ProjectiveMethod entity) {
        return meetMapper.fromEntityToResponse(entity.getMeet());
    }
}
