package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.NotificationResponse;
import ru.company.understandablepractice.model.Customer;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public abstract class NotificationMapper {

    @Mapping(target = "customerId", expression = "java(mapCustomerId(customer))")
    @Mapping(target = "dateFirstRequest", expression = "java(mapDateFirstRequest(customer))")
    @Mapping(target = "customerFullName", expression = "java(mapCustomerFullName(customer))")
    public abstract NotificationResponse fromEntityToResponse(Customer customer);

    long mapCustomerId(Customer customer) {
        return customer.getId();
    }

    LocalDate mapDateFirstRequest(Customer customer) {
        return customer.getDateFirstRequest();
    }

    String mapCustomerFullName(Customer customer) {
        return customer.getFullName();
    }
}
