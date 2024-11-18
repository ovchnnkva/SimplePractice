package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.NotificationResponse;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.Person;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public abstract class NotificationMapper {

    @Mapping(target = "customerId", expression = "java(mapCustomerId(entity))")
    @Mapping(target = "dateFirstRequest", expression = "java(mapDateFirstRequest(entity))")
    @Mapping(target = "customerFullName", expression = "java(mapCustomerFullName(entity))")
    @Mapping(target = "clientType", expression = "java(mapClientType(entity))")
    public abstract NotificationResponse fromEntityToResponse(Person entity);

    long mapCustomerId(Person entity) {
        return entity.getId();
    }

    LocalDate mapDateFirstRequest(Person entity) {
        return entity.getDateFirstRequest();
    }

    String mapCustomerFullName(Person entity) {
        return entity.getFullName();
    }

    String mapClientType(Person entity) {
        return entity.getClientType().getTittle();
    }
}
