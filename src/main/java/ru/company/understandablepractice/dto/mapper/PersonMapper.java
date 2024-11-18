package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.PersonResponse;
import ru.company.understandablepractice.model.Person;
import ru.company.understandablepractice.model.types.Gender;

import java.util.Arrays;

@Mapper(componentModel = "spring")
public abstract class PersonMapper {

    @Mapping(target = "gender", expression = "java(mapGender(response))")
    public abstract Person fromResponseToEntity(PersonResponse response);

    public abstract PersonResponse fromEntityToResponse(Person entity);


    Gender mapGender(PersonResponse response) {
        return Arrays.stream(Gender.values())
                .filter(value -> value.getTittle().equals(response.getGender()))
                .findFirst()
                .orElse(null);
    }

    String mapFullName(Person person) {
        return String.format("%s %s %s", person.getLastName(), person.getFirstName(), person.getSecondName());
    }
}
