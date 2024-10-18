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
import ru.company.understandablepractice.dto.projectivemethod.ProjectiveMethodDetailsResponse;
import ru.company.understandablepractice.service.ProjectiveMethodService;

import java.util.List;

@Tag(name = "Проективные методики")
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/General/projMethods")
public class ProjectiveMethodsDetailsController {

    private final ProjectiveMethodService service;

    private final HttpServletRequest request;

    @Operation(summary = "Проективные методики для встречи", description = "Позволяет получить все проективные методики по id встречи")
    @GetMapping("/{offset}/{limit}")
    public ResponseEntity<List<ProjectiveMethodDetailsResponse>> getProjectiveMethods(@PathVariable @Parameter(description = "meetId") long meetId) {
        return service.findProjectiveMethodByMeetId(meetId)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
