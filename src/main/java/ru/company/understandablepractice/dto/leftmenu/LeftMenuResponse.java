package ru.company.understandablepractice.dto.leftmenu;

import lombok.Data;
import lombok.Setter;
import ru.company.understandablepractice.dto.ErrorResponse;
import ru.company.understandablepractice.dto.WarningResponse;

import java.time.LocalDateTime;

@Data
@Setter
public class LeftMenuResponse {
    private String status;

    private String actualTimestamp;

    private LeftMenuClientDataResponse clientData;

    private ErrorResponse error = new ErrorResponse();

    private WarningResponse warning = new WarningResponse();
}
