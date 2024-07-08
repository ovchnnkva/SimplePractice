package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.CustomerResponse;
import ru.company.understandablepractice.dto.PairResponse;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.Pair;
import ru.company.understandablepractice.model.types.ClientType;
import ru.company.understandablepractice.model.types.FamilyStatus;
import ru.company.understandablepractice.model.types.Gender;

import java.util.Arrays;

@Mapper(componentModel = "spring")
public abstract class PairMapper {
    @Autowired
    private CustomerMapper customerMapper;

    @Mapping(target = "clientType", expression = "java(mapClientType(response))")
    @Mapping(target = "firstCustomer", expression = "java(mapFirstCustomer(response))")
    @Mapping(target = "secondCustomer", expression = "java(mapSecondCustomer(response))")
    @Mapping(target = "familyStatus", expression = "java(mapFamilyStatus(response))")
    @Mapping(target = "gender", expression = "java(mapGender(response))")
    public abstract Pair fromResponseToEntity(PairResponse response);

    @Mapping(target = "clientType", expression = "java(mapCLientTypeString(entity))")
    @Mapping(target = "firstCustomer", expression = "java(mapFirstCustomerResponse(entity))")
    @Mapping(target = "secondCustomer", expression = "java(mapSecondCustomerResponse(entity))")
    @Mapping(target = "familyStatus", expression = "java(mapFamilyStatusString(entity))")
    @Mapping(target = "gender", expression = "java(mapGenderString(entity))")
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

    FamilyStatus mapFamilyStatus(PairResponse response) {
        return Arrays.stream(FamilyStatus.values())
                .filter(value -> value.getTittle().equals(response.getFamilyStatus()))
                .findFirst()
                .orElseThrow();
    }

    String mapFamilyStatusString(Pair entity) {
        return entity.getFamilyStatus().getTittle();
    }

    ClientType mapClientType(PairResponse response) {
        return Arrays.stream(ClientType.values())
                .filter(value -> value.getTittle().equals(response.getClientType()))
                .findFirst()
                .orElseThrow();
    }

    String mapCLientTypeString(Pair entity) {
        return entity.getClientType().getTittle();
    }

    Gender mapGender(PairResponse response) {
        return Arrays.stream(Gender.values())
                .filter(value -> value.getTittle().equals(response.getGender()))
                .findFirst()
                .orElseThrow();
    }

    String mapGenderString(Pair entity) {
        return entity.getGender().getTittle();
    }
}
