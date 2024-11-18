package ru.company.understandablepractice.controller;

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
import ru.company.understandablepractice.dto.SearchMeetResponse;
import ru.company.understandablepractice.dto.mapper.SearchMeetMapper;
import ru.company.understandablepractice.model.Meet;
import ru.company.understandablepractice.security.services.JwtService;
import ru.company.understandablepractice.security.JwtType;
import ru.company.understandablepractice.service.MeetService;

import java.util.List;
import java.util.stream.Collectors;

@Tag(
        name = "Search meet's"
)
@RestController
@RequestMapping("/api/v1/General/searchMeet")
@RequiredArgsConstructor
@Slf4j
public class SearchMeetController {

    private final MeetService service;
    private final SearchMeetMapper mapper;
    private final HttpServletRequest request;
    private final JwtService jwtService;
    @GetMapping("{customerId}/{limit}/{offset}")
    public ResponseEntity<List<SearchMeetResponse>> getMeetsByUserAndPerson(@PathVariable @Parameter(description = "ИД Клиента") long customerId,
                                                                            @PathVariable @Parameter(description = "offset") long offset,
                                                                            @PathVariable @Parameter(description = "limit") long limit) {
        Long userId = jwtService.extractUserId(request.getHeader("Authorization"), JwtType.ACCESS);
        log.info("get meets by userId = {}, personId = {}", userId, customerId);
        List<Meet> result = service.getByUserIdAndCustomerId(userId, customerId, offset, limit);
        return result.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(result.stream()
                .map(mapper::fromEntityToResponse)
                .collect(Collectors.toList()), HttpStatus.OK);
    }
}
