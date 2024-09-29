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
import ru.company.understandablepractice.dto.HeaderSearchPersonResponse;
import ru.company.understandablepractice.security.JwtType;
import ru.company.understandablepractice.security.services.JwtService;
import ru.company.understandablepractice.service.HeaderService;

import java.util.List;

@Tag(
        name = "Header"
)
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/Header/")
public class HeaderController {
    private final HeaderService headerService;
    private final HttpServletRequest request;
    private final JwtService jwtService;

    @Operation(summary = "Клиенты найденные по имени", description = "Позволяет получить всех пользователей по заданому имени")
    @GetMapping("searchPersons/{personName}")
    public ResponseEntity<List<HeaderSearchPersonResponse>> getPersonsByName(@PathVariable @Parameter(description = "Имя клиента") String personName) {

        long userId = jwtService.extractUserId(request.getHeader("Authorization"), JwtType.ACCESS);
        log.info("auth user {}", userId);
        return headerService.findPersonsByName(userId, personName)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
