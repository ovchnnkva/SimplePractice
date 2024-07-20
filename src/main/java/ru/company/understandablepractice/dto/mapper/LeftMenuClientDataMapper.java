package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.leftmenu.LeftMenuClientDataResponse;
import ru.company.understandablepractice.dto.leftmenu.LeftMenuClientReqResponse;
import ru.company.understandablepractice.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class LeftMenuClientDataMapper {

    @Mapping(target = "clientRequests", source = "clientRequests")
    public abstract LeftMenuClientDataResponse toResponse(User user, List<LeftMenuClientReqResponse> clientRequests);
}
