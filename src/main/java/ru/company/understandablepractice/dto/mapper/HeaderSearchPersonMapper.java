package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.HeaderSearchPersonResponse;
import ru.company.understandablepractice.model.Customer;

@Mapper(componentModel = "spring")
public abstract class HeaderSearchPersonMapper {

    @Mapping(target = "customerId", expression = "java(mapPersonId(customer))")
    @Mapping(target = "fullName", expression = "java(mapFullName(customer))")
    @Mapping(target = "clientType", expression = "java(mapClientType(customer))")
    public abstract HeaderSearchPersonResponse fromEntityToResponse(Customer customer);

    long mapPersonId(Customer customer) {
        return customer.getId();
    }

    String mapFullName(Customer customer) {
        return customer.getFullName();
    }

    String mapClientType(Customer customer) {
        return customer.getClientType().getTittle();
    }
}
