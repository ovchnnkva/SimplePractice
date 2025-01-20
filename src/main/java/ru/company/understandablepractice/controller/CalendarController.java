package ru.company.understandablepractice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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


    @Operation(summary = "Встречи на текущий год", description = "Позволяет получить все встречи пользователя на текущий год")
    @GetMapping("/get/{year}")
    public ResponseEntity<CalendarResponse> getCalendar(@PathVariable(name = "year") @Parameter(description = "Год, в рамках которого находятся встречи") int year,
                                                        @RequestParam(required = false) @Parameter(description = "фильтр по статусу клиента") String clientStatus,
                                                        @RequestParam(required = false) @Parameter(description = "фильтр по типу клиента") String clientType,
                                                        @RequestParam(required = false) @Parameter(description = "фильтр по формату встречи") String formatMeet) {

        log.info("get calendar. Client status {}, client type {}, meet format {}", clientStatus, clientType, formatMeet);
        try{
            return calendarService.getCalendar(year, clientStatus, clientType, formatMeet)
                    .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); //
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
