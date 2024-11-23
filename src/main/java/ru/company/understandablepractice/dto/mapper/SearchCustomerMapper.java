package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.SearchCustomerResponse;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.types.ClientStatus;
import ru.company.understandablepractice.model.types.MeetingFormat;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class SearchCustomerMapper {

    @Mapping(target = "customerId", expression = "java(mapCustomerId(customer))")
    @Mapping(target = "fullName", expression = "java(mapFullName(customer))")
    @Mapping(target = "years", expression = "java(mapYears(customer))")
    @Mapping(target = "clientType", expression = "java(mapClientType(customer))")
    @Mapping(target = "mail", expression = "java(mapMail(customer))")
    @Mapping(target = "phone", expression = "java(mapPhone(customer))")
    @Mapping(target = "meetDate", expression = "java(mapMeetDate(meetDates))")
    @Mapping(target = "countMeet", expression = "java(mapMeetCount(meetDates))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatus(customer))")
    @Mapping(target = "meetingType", expression = "java(mapMeetingType(customer))")
    public abstract SearchCustomerResponse fromEntityToResponse(Customer customer, List<LocalDate> meetDates);

    long mapCustomerId(Customer customer) {
        return customer.getId();
    }

    String mapFullName(Customer customer){
        return customer.getFullName();
    }

    int mapYears(Customer customer){
        return Period.between(Optional.ofNullable(customer.getBirth()).orElse(LocalDate.now()), LocalDate.now()).getYears();
    }

    String mapClientType(Customer customer){
        return customer.getClientType().getTittle();
    }

    String mapMail(Customer customer){
        return customer.getMail();
    }

    String mapPhone(Customer customer){
        return customer.getPhoneNumber();
    }

    LocalDate mapMeetDate(List<LocalDate> meetDates){
        return meetDates.stream().findFirst().orElse(null);
    }

    int mapMeetCount(List<LocalDate> meetDates) {
        return meetDates.size();
    }

    String mapClientStatus(Customer customer){
        return Optional.ofNullable(customer.getClientStatus()).map(ClientStatus::getTittle).orElse(null);
    }

    String mapMeetingType(Customer customer){
        return Optional.ofNullable(customer.getMeetingFormat()).map(MeetingFormat::getTittle).orElse(null);
    }
}
