package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.MeetResponse;
import ru.company.understandablepractice.dto.projectivemethod.PhotoProjectiveMethodResponse;
import ru.company.understandablepractice.dto.projectivemethod.ProjectiveMethodDetailsResponse;
import ru.company.understandablepractice.dto.projectivemethod.TypeMethodResponse;
import ru.company.understandablepractice.model.PhotoProjectiveMethod;
import ru.company.understandablepractice.model.ProjectiveMethod;
import ru.company.understandablepractice.model.TypeMethod;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ProjectiveMethodDetailsMapper {

    @Autowired
    private MeetMapper meetMapper;

    @Autowired
    private TypeMethodMapper typeMethodMapper;

    @Autowired
    private PhotoProjectiveMethodMapper photoProjectiveMethodMapper;

    @Mapping(target = "meet", expression = "java(mapMeet(entity))")
    @Mapping(target = "dateCreateMethod", expression = "java(mapDateCreateMethod(entity))")
    @Mapping(target = "typeMethod", expression = "java(mapTypeMethod(entity))")
    @Mapping(target = "photoProjectiveMethod", expression = "java(mapPhotoProjectiveMethod(photoProjectiveMethodList))")
    public abstract ProjectiveMethodDetailsResponse fromEntityToResponse(ProjectiveMethod entity,
                                                                         List<PhotoProjectiveMethod> photoProjectiveMethodList);

    MeetResponse mapMeet(ProjectiveMethod entity) {
        return meetMapper.fromEntityToResponse(entity.getMeet());
    }

    LocalDate mapDateCreateMethod(ProjectiveMethod entity) {
        return entity.getDateCreateMethod();
    }

    TypeMethodResponse mapTypeMethod(ProjectiveMethod entity) {
        return typeMethodMapper.fromEntityToResponse(entity.getTypeMethod());
    }

    List<PhotoProjectiveMethodResponse> mapPhotoProjectiveMethod(List<PhotoProjectiveMethod> photoProjectiveMethodList) {
        return photoProjectiveMethodList.stream().map(e -> photoProjectiveMethodMapper.fromEntityToResponse(e)).collect(Collectors.toList());
    }
}
