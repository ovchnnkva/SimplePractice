package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.HeaderSearchPersonResponse;
import ru.company.understandablepractice.model.Person;

@Mapper(componentModel = "spring")
public abstract class HeaderSearchPersonMapper {

    @Mapping(target = "personId", expression = "java(mapPersonId(person))")
    @Mapping(target = "fullName", expression = "java(mapFullName(person))")
    public abstract HeaderSearchPersonResponse fromEntityToResponse(Person person);

    long mapPersonId(Person person) {
        return person.getId();
    }

    String mapFullName(Person person) {
        return person.getFullName();
    }
}
