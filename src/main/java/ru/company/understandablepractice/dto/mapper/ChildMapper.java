package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.company.understandablepractice.dto.ChildResponse;
import ru.company.understandablepractice.dto.CustomerResponse;
import ru.company.understandablepractice.model.Child;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.types.BringsClient;
import ru.company.understandablepractice.model.types.ClientType;
import ru.company.understandablepractice.model.types.Gender;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Mapper(componentModel = "spring")
public abstract class ChildMapper {

    @Autowired
    private CustomerMapper customerMapper;

    @Mapping(target = "clientType", expression = "java(mapClientType(response))")
    @Mapping(target = "bringsClient", expression = "java(mapBringsClient(response))")
    @Mapping(target = "firstParent", expression = "java(mapFirstParent(response))")
    @Mapping(target = "secondParent", expression = "java(mapSecondParent(response))")
    @Mapping(target = "gender", expression = "java(mapGender(response))")
    public abstract Child fromResponseToEntity(ChildResponse response);

    @Mapping(target = "bringsClient", expression = "java(mapBringsClientString(child))")
    @Mapping(target = "firstParent", expression = "java(mapFirstParentResponse(child))")
    @Mapping(target = "secondParent", expression = "java(mapSecondParentResponse(child))")
    @Mapping(target = "gender", expression = "java(mapGenderString(child))")
    public abstract ChildResponse fromEntityToResponse(Child child);

    ClientType mapClientType(ChildResponse response) {
        return Arrays.stream(ClientType.values())
                .filter(value -> value.getTittle().equals(response.getClientType()))
                .findFirst()
                .orElseThrow();
    }

    String mapCLientTypeString(Child entity) {
        return entity.getClientType().getTittle();
    }
    String mapBringsClientString(Child child){
        return child.getBringsClient().getTitle();
    }

    BringsClient mapBringsClient(ChildResponse response) throws NoSuchElementException{
        return Arrays.stream(BringsClient.values())
                .filter(bringsClient -> bringsClient.getTitle().equals(response.getBringsClient()))
                .findFirst()
                .orElseThrow();
    }

    CustomerResponse mapFirstParentResponse(Child child){
        return customerMapper.fromEntityToResponse(child.getFirstParent());
    }

    Customer mapFirstParent(ChildResponse response){
        return customerMapper.fromResponseToEntity(response.getFirstParent());
    }

    CustomerResponse mapSecondParentResponse(Child child){
        return customerMapper.fromEntityToResponse(child.getSecondParent());
    }

    Customer mapSecondParent(ChildResponse response){
        return customerMapper.fromResponseToEntity(response.getSecondParent());
    }

    Gender mapGender(ChildResponse response) {
        return Arrays.stream(Gender.values())
                .filter(value -> value.getTittle().equals(response.getGender()))
                .findFirst()
                .orElseThrow();
    }

    String mapGenderString(Child entity) {
        return entity.getGender().getTittle();
    }
}
