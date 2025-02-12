package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.*;
import ru.company.understandablepractice.dto.PersonResponse;
import ru.company.understandablepractice.model.Person;
import ru.company.understandablepractice.model.types.Gender;

import java.util.Arrays;

@Mapper(componentModel = "spring")
public abstract class PersonMapper {

    @Mapping(target = "gender", expression = "java(mapGender(response))")
    @Mapping(target = "fullName", expression = "java(mapFullName(response))")
    public abstract Person fromResponseToEntity(PersonResponse response);

    @Mapping(target = "gender", expression = "java(mapGenderString(entity))")
    public abstract PersonResponse fromEntityToResponse(Person entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "gender", expression = "java(mapGender(response))")
    @Mapping(target = "fullName", expression = "java(mapFullName(response))")
    public abstract void updateEntityFromDto(PersonResponse response, @MappingTarget Person entity);


    Gender mapGender(PersonResponse response) {
        return Arrays.stream(Gender.values())
                .filter(value -> value.getTittle().equals(response.getGender()))
                .findFirst()
                .orElse(null);
    }

    String mapGenderString(Person entity) {
        return entity.getGender() != null ? entity.getGender().getTittle() : null;
    }

    String mapFullName(PersonResponse response) {
        return String.format("%s %s %s", response.getLastName(), response.getFirstName(), response.getSecondName());
    }
}
