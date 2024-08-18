package ru.company.understandablepractice.dto.calendar;

import lombok.Data;
import lombok.Setter;
import ru.company.understandablepractice.dto.ErrorResponse;
import ru.company.understandablepractice.dto.WarningResponse;

import java.time.LocalDate;
import java.util.List;
@Data
@Setter
public class CalendarResponse {
    private String currentDate;

    private List<CalendarClientDataResponse> clientsData;
}
