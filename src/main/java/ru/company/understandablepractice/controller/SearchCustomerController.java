package ru.company.understandablepractice.controller;

import ch.qos.logback.core.util.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.understandablepractice.dto.SearchCustomerResponse;
import ru.company.understandablepractice.dto.mapper.SearchCustomerMapper;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.security.services.JwtService;
import ru.company.understandablepractice.security.JwtType;
import ru.company.understandablepractice.service.SearchCustomerService;
import ru.company.understandablepractice.specification.CustomerSpecification;
import ru.company.understandablepractice.utils.SortUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(
        name = "Search Persons by name"
)
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/General/searchPersons/")
public class SearchCustomerController {
    private final SearchCustomerService searchService;
    private final SearchCustomerMapper searchCustomerMapper;

    @Operation(summary = "Клиенты найденные по имени", description = "Позволяет получить всех пользователей по заданому имени")
    @GetMapping("/{offset}/{limit}")
    public ResponseEntity<List<SearchCustomerResponse>> getCustomersByName(@PathVariable @Parameter(description = "offset") int offset,
                                                                           @PathVariable @Parameter(description = "limit") int limit,
                                                                           @RequestParam(name = "customerName", required = false) @Parameter(description = "Имя клиента") String customerName,
                                                                           @RequestParam(name = "orderDate", required = false) String orderDate,
                                                                           @RequestParam(name = "orderMeetCount", required = false) String orderMeetCount,
                                                                           @RequestParam(required = false) @Parameter(description = "фильтр по типу клиента") String clientType,
                                                                           @RequestParam(required = false) @Parameter(description = "фильтр по статусу киента") String clientStatus) {

                return new ResponseEntity<>(
                        searchService.findByName(customerName, PageRequest.of(offset, limit), orderDate, orderMeetCount, clientStatus, clientType),
                        HttpStatus.OK
                );
    }
}
