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
    @Mapping(target = "applicationFormStatus", expression = "java(mapApplicationForm(entity))")
    public abstract NotificationResponse fromEntityToResponse(Customer entity);

    long mapCustomerId(Customer entity) {
        return entity.getId();
    }

    LocalDate mapDateFirstRequest(Customer entity) {
        return entity.getDateFirstRequest();
    }

    String mapCustomerFullName(Customer entity) {
        return entity.getFullName();
    }

    String mapClientType(Customer entity) {
        return entity.getClientType().getTittle();
    }

    int mapApplicationForm(Customer customer) {
        return customer.getApplicationFormStatus() != null ? customer.getApplicationFormStatus().getKey() : 2;
    }
}
