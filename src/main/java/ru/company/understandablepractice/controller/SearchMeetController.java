package ru.company.understandablepractice.controller;

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
import ru.company.understandablepractice.dto.SearchMeetResponse;
import ru.company.understandablepractice.dto.mapper.SearchMeetMapper;
import ru.company.understandablepractice.model.Meet;
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

    @GetMapping("{userId}/{personId}")
    public ResponseEntity<List<SearchMeetResponse>> getMeetsByUserAndPerson(@PathVariable @Parameter(description = "ИД Пользователя") long userId,
                                                                           @PathVariable @Parameter(description = "ИД Клиента") long personId) {
        log.info("get meets by userId = {}, personId = {}", userId, personId);
        List<Meet> result = service.getByUserIdAndPersonId(userId, personId);
        return result.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(result.stream()
                .map(mapper::fromEntityToResponse)
                .collect(Collectors.toList()), HttpStatus.OK);
    }
}
