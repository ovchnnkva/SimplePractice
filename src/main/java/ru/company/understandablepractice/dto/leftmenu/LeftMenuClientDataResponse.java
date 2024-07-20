package ru.company.understandablepractice.dto.leftmenu;

import lombok.Data;

import java.util.List;

@Data
public class LeftMenuClientDataResponse {
    private LeftMenuUserDataResponse userData;

    private List<LeftMenuButtonResponse> leftMenuButtons;

    private List<LeftMenuClientReqResponse> clientRequests;
}
