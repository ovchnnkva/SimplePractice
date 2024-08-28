package ru.company.understandablepractice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.company.understandablepractice.dto.SearchPersonResponse;
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

    @Operation(summary = "Клиенты найденные по имени", description = "Позволяет получить всех пользователей по заданому имени")
    @GetMapping("/{personName}")
    public List<SearchPersonResponse> getPersonsByName(@PathVariable @Parameter(description = "Имя клиента") String personName) {
        return searchService.findByName(personName);
    }
}
