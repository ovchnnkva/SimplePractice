package ru.company.understandablepractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.dto.calendar.CalendarResponse;
import ru.company.understandablepractice.dto.mapper.CalendarClientDataMapper;
import ru.company.understandablepractice.dto.mapper.CalendarMeetMapper;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.Meet;
import ru.company.understandablepractice.model.Person;
import ru.company.understandablepractice.repository.MeetRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.company.understandablepractice.utils.DateFormatUtil.formatDateToString;
import static ru.company.understandablepractice.utils.HttpErrorResponse.getNotFoundResponse;

@Service
@RequiredArgsConstructor
public class CalendarService {
    private final CalendarMeetMapper calendarMeetMapper;
    private final CalendarClientDataMapper calendarClientDataMapper;
    private final MeetRepository meetRepository;
    public CalendarResponse getCalendar(long userId, String year) {
        CalendarResponse response = new CalendarResponse();

        response.setCurrentDate(formatDateToString(LocalDate.now()));
        Map<Person, List<Meet>> meetings = meetRepository.findByUserIdAndYear(userId, year)
                .stream()
                .collect(Collectors.groupingBy(Meet::getCustomer));

        if (meetings.isEmpty()) {
            response.setError(getNotFoundResponse());
        } else {
            response.setClientsData(meetings.entrySet()
                    .stream()
                    .map(entry -> calendarClientDataMapper.toResponse(
                                    entry.getKey(),
                                    entry.getValue()
                                            .stream()
                                            .map(calendarMeetMapper::fromEntityToResponse)
                                            .collect(Collectors.toList())
                            )
                    )
                    .collect(Collectors.toList())
            );
        }
        return response;
    }
}