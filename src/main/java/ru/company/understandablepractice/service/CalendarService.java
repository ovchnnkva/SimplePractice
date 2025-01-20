package ru.company.understandablepractice.service;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.dto.calendar.CalendarResponse;
import ru.company.understandablepractice.dto.mapper.CalendarClientDataMapper;
import ru.company.understandablepractice.dto.mapper.CalendarMeetMapper;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.Meet;
import ru.company.understandablepractice.model.types.converters.ClientStatusConverter;
import ru.company.understandablepractice.model.types.converters.ClientTypeConverter;
import ru.company.understandablepractice.model.types.converters.MeetingFormatConverter;
import ru.company.understandablepractice.repository.MeetRepository;
import ru.company.understandablepractice.security.JwtType;
import ru.company.understandablepractice.security.services.JwtService;
import ru.company.understandablepractice.specification.CalendarSpecification;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.company.understandablepractice.utils.DateFormatUtil.formatDateToString;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarService {
    private final CalendarMeetMapper calendarMeetMapper;
    private final CalendarClientDataMapper calendarClientDataMapper;
    private final MeetRepository meetRepository;
    private final HttpServletRequest request;
    private final JwtService jwtService;
    private final ClientStatusConverter clientStatusConverter = new ClientStatusConverter();
    private final ClientTypeConverter clientTypeConverter = new ClientTypeConverter();
    private final MeetingFormatConverter meetingFormatConverter = new MeetingFormatConverter();

    public Optional<CalendarResponse> getCalendar(int year, String clientStatus, String clientType, String format) {
        CalendarResponse response = null;
        Long userId = jwtService.extractUserId(request.getHeader("Authorization"), JwtType.ACCESS);
        Map<Customer, List<Meet>> meetings = meetRepository.findAll(buildSpec(userId, year, clientStatus, clientType, format))
                .stream()
                .collect(Collectors.groupingBy(meet -> meet.getCustomer() != null ? meet.getCustomer() : new Customer(0)));

        if (!meetings.isEmpty()) {
            response = new CalendarResponse();
            response.setCurrentDate(formatDateToString(LocalDate.now()));
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
        return Optional.ofNullable(response);
    }

    private Specification<Meet> buildSpec(long userId, int year, String clientStatus, String clientType, String format) {
        return Specification.where(CalendarSpecification.hasUser(userId)
                .and(CalendarSpecification.hasStartDate(year))
                .and(CalendarSpecification.hasEndDate(year))
                .and(CalendarSpecification.hasClientStatus(clientStatusConverter.convertToEntityAttribute(clientStatus)))
                .and(CalendarSpecification.hasClientType(clientTypeConverter.convertToEntityAttribute(clientType)))
                .and(CalendarSpecification.hasFormat(meetingFormatConverter.convertToEntityAttribute(format))));

    }
}
