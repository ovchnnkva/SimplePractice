package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.leftmenu.LeftMenuClientReqResponse;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public abstract class LeftMenuClientReqMapper {

    @Mapping(target = "date", expression = "java(mapDate())")
    @Mapping(target = "clientName", expression = "java(mapClientName(clientName))")
    @Mapping(target = "clientCardUrl", expression = "java(mapClientCardUrl(clientCardUrl))")
    public abstract LeftMenuClientReqResponse fromEntityToResponse(String clientName, String clientCardUrl);

    LocalDateTime mapDate(){
        return LocalDateTime.now();
    }

    String mapClientName(String clientName){
        return "Client name: " + clientName;
    }

    String mapClientCardUrl(String clientCardUrl){
        return "Client card url: " + clientCardUrl;
    }
}
