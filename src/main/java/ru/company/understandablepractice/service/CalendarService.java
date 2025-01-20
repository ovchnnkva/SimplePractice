package ru.company.understandablepractice.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
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
import ru.company.understandablepractice.specification.CalendarSpecification;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.company.understandablepractice.utils.DateFormatUtil.formatDateToString;

@Service
@RequiredArgsConstructor
public class CalendarService {
    private final CalendarMeetMapper calendarMeetMapper;
    private final CalendarClientDataMapper calendarClientDataMapper;
    private final MeetRepository meetRepository;
    private ClientStatusConverter clientStatusConverter;
    private ClientTypeConverter clientTypeConverter;
    private MeetingFormatConverter meetingFormatConverter;

    @PostConstruct
    private void init() {
        this.clientStatusConverter = new ClientStatusConverter();
        this.clientTypeConverter = new ClientTypeConverter();
        this.meetingFormatConverter = new MeetingFormatConverter();
    }
    public Optional<CalendarResponse> getCalendar(long userId, int year, String clientStatus, String clientType, String format) {
        CalendarResponse response = null;
        Specification<Meet> spec = Specification.where(CalendarSpecification.hasUser(userId)
                .and(CalendarSpecification.hasStartDate(year))
                .and(CalendarSpecification.hasEndDate(year))
                .and(CalendarSpecification.hasClientStatus(clientStatusConverter.convertToEntityAttribute(clientStatus)))
                .and(CalendarSpecification.hasClientType(clientTypeConverter.convertToEntityAttribute(clientType)))
                .and(CalendarSpecification.hasFormat(meetingFormatConverter.convertToEntityAttribute(format))));

        Map<Customer, List<Meet>> meetings = meetRepository.findAll(spec)
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
}
