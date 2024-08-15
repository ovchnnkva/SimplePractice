package ru.company.understandablepractice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.understandablepractice.dto.CustomerResponse;
import ru.company.understandablepractice.dto.mapper.CustomerMapper;
import ru.company.understandablepractice.model.types.ClientType;
import ru.company.understandablepractice.service.CustomerService;

@Tag(
        name = "Клиенты",
        description = "Операции над Клиентами"
)
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService service;

    private final CustomerMapper mapper;

    @Operation(summary = "Получение по ID", description = "Позволяет получить клиента по ключу")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable(name = "id") @Parameter(description = "ID клиента") long id) {
        log.info("get user by id {}", id);
        return service.getById(id)
                .map(value -> new ResponseEntity<>(mapper.fromEntityToResponse(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Обновление", description = "Позволяет обновить данные клиента")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Parameter(description = "Клиент") CustomerResponse response) {
        log.info("update customer {}", response);
        return service.create(mapper.fromResponseToEntity(response))
                .map(value -> new ResponseEntity<>(HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Operation(summary = "Создать", description = "Создать клиента")
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Parameter(description = "Клиент") CustomerResponse response) {
        log.info("create customer {}", response);
        return service.create(mapper.fromResponseToEntity(response))
                .map(value -> new ResponseEntity<>(value.getId(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Operation(summary = "Удалить", description = "Удаление клиента")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") @Parameter(description = "ID клиента") long id) {
        log.info("delete customer {}", id);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
