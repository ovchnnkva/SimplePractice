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
import ru.company.understandablepractice.dto.SearchPersonResponse;
import ru.company.understandablepractice.security.services.JwtService;
import ru.company.understandablepractice.security.JwtType;
import ru.company.understandablepractice.service.SearchPersonService;

import java.util.List;

@Tag(
        name = "Search Persons by name"
)
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/General/searchPersons/")
public class SearchPersonController {
    private final SearchPersonService searchService;
    private final HttpServletRequest request;
    private final JwtService jwtService;

    @Operation(summary = "Клиенты найденные по имени", description = "Позволяет получить всех пользователей по заданому имени")
    @GetMapping("/{personName}/{offset}/{limit}")
    public ResponseEntity<List<SearchPersonResponse>> getPersonsByName(@PathVariable @Parameter(description = "Имя клиента") String personName,
                                                                       @PathVariable @Parameter(description = "offset") long offset,
                                                                       @PathVariable @Parameter(description = "limit") long limit) {
        Long userId = jwtService.extractUserId(request.getHeader("Authorization"), JwtType.ACCESS);
        log.info("auth user {}", userId);
        return searchService.findByName(userId, personName, offset, limit)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
