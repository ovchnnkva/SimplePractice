package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.PersonResponse;
import ru.company.understandablepractice.dto.SearchMeetResponse;
import ru.company.understandablepractice.model.Meet;
import ru.company.understandablepractice.model.Person;

@Mapper(componentModel = "spring")
public abstract class SearchMeetMapper {

    @Mapping(target = "formatMeet", expression = "java(mapMeetingFormatString(entity))")
    public abstract SearchMeetResponse fromEntityToResponse(Meet entity);

    String mapMeetingFormatString(Meet entity) {
        return entity.getFormatMeet().getTittle();
    }
}
