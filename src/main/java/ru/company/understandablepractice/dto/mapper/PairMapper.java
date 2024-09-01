package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.CustomerResponse;
import ru.company.understandablepractice.dto.PairResponse;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.Pair;
import ru.company.understandablepractice.model.types.*;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Mapper(componentModel = "spring")
public abstract class PairMapper {
    @Autowired
    private CustomerMapper customerMapper;

    @Mapping(target = "clientType", expression = "java(mapClientType(response))")
    @Mapping(target = "familyStatus", expression = "java(mapFamilyStatus(response))")
    @Mapping(target = "firstCustomer", expression = "java(mapFirstCustomer(response))")
    @Mapping(target = "secondCustomer", expression = "java(mapSecondCustomer(response))")
    @Mapping(target = "gender", expression = "java(mapGender(response))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatus(response))")
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormat(response))")
    public abstract Pair fromResponseToEntity(PairResponse response);

    @Mapping(target = "familyStatus", expression = "java(mapFamilyStatusString(entity))")
    @Mapping(target = "firstCustomer", expression = "java(mapFirstCustomerResponse(entity))")
    @Mapping(target = "secondCustomer", expression = "java(mapSecondCustomerResponse(entity))")
    @Mapping(target = "gender", expression = "java(mapGenderString(entity))")
    @Mapping(target = "clientStatus", expression = "java(mapClientStatusString(entity))")
    @Mapping(target = "meetingFormat", expression = "java(mapMeetingFormatString(entity))")
    public abstract PairResponse fromEntityToResponse(Pair entity);

    Customer mapFirstCustomer(PairResponse response) {
        return customerMapper.fromResponseToEntity(response.getFirstCustomer());
    }

    CustomerResponse mapFirstCustomerResponse(Pair entity) {
        return customerMapper.fromEntityToResponse(entity.getFirstCustomer());
    }

    Customer mapSecondCustomer(PairResponse response) {
        return customerMapper.fromResponseToEntity(response.getSecondCustomer());
    }

    CustomerResponse mapSecondCustomerResponse(Pair entity) {
        return customerMapper.fromEntityToResponse(entity.getSecondCustomer());
    }

    ClientType mapClientType(PairResponse response) {
        return Arrays.stream(ClientType.values())
                .filter(value -> value.getTittle().equals(response.getClientType()))
                .findFirst()
                .orElse(null);
    }

    String mapCLientTypeString(Pair entity) {
        return entity.getClientType().getTittle();
    }

    Gender mapGender(PairResponse response) {
        return Arrays.stream(Gender.values())
                .filter(value -> value.getTittle().equals(response.getGender()))
                .findFirst()
                .orElse(null);
    }

    String mapGenderString(Pair entity) {
        return entity.getGender().getTittle();
    }

    String mapFamilyStatusString(Pair entity) {
        return entity.getClientType().getTittle();
    }

    FamilyStatus mapFamilyStatus(PairResponse response) {
        return Arrays.stream(FamilyStatus.values())
                .filter(value -> value.getTittle().equals(response.getFamilyStatus()))
                .findFirst()
                .orElse(null);
    }

    String mapClientStatusString(Pair entity) {
        return entity.getClientStatus().getTittle();
    }

    ClientStatus mapClientStatus(PairResponse response) throws NoSuchElementException {
        return Arrays.stream(ClientStatus.values())
                .filter(status -> status.getTittle().equals(response.getClientStatus()))
                .findFirst()
                .orElse(null);
    }

    String mapMeetingFormatString(Pair entity) {
        return entity.getMeetingFormat().getTittle();
    }

    MeetingFormat mapMeetingFormat(PairResponse response) {
        return Arrays.stream(MeetingFormat.values())
                .filter(status -> status.getTittle().equals(response.getMeetingFormat()))
                .findFirst()
                .orElse(null);
    }
}
