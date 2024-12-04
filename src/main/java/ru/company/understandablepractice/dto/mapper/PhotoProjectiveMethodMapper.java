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

    public abstract PhotoProjectiveMethod fromResponseToEntity(PhotoProjectiveMethodResponse response);

    public abstract PhotoProjectiveMethodResponse fromEntityToResponse(PhotoProjectiveMethod entity);

}
