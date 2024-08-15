package ru.company.understandablepractice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.company.understandablepractice.dto.calendar.CalendarResponse;
import ru.company.understandablepractice.service.CalendarService;

import java.net.http.HttpResponse;

@Tag(
        name = "Calendar Functionality"
)
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/General/calendarData/")
public class CalendarController {
    private final CalendarService calendarService;

    @Operation(summary = "Встречи на текущий год", description = "Позволяет получить все встречи пользователя на текущий год")
    @GetMapping("/{userId}/{year}")
    public CalendarResponse getCalendar(@PathVariable("userId") @Parameter(name = "ID Пользователя") long userId,
                                        @PathVariable("year") @Parameter(name = "Год, в рамках которого находятся встречи") String year) {

        return calendarService.getCalendar(userId, year); //
    }
}
