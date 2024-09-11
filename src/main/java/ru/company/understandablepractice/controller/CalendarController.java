package ru.company.understandablepractice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.company.understandablepractice.dto.calendar.CalendarResponse;
import ru.company.understandablepractice.security.services.JwtService;
import ru.company.understandablepractice.security.JwtType;
import ru.company.understandablepractice.service.CalendarService;

@Tag(
        name = "Calendar Functionality"
)
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/General/calendarData/")
public class CalendarController {
    private final CalendarService calendarService;
    private final HttpServletRequest request;
    private final JwtService jwtService;

    @Operation(summary = "Встречи на текущий год", description = "Позволяет получить все встречи пользователя на текущий год")
    @GetMapping("/get/{year}")
    public ResponseEntity<CalendarResponse> getCalendar(@PathVariable(name = "year") @Parameter(description = "Год, в рамках которого находятся встречи") String year) {

        Long userId = jwtService.extractUserId(request.getHeader("Authorization"), JwtType.ACCESS);
        log.info("auth user {}", userId);
        return calendarService.getCalendar(userId, year)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); //
    }
}
