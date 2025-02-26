package ru.company.understandablepractice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.understandablepractice.dto.customers.PairResponse;
import ru.company.understandablepractice.dto.mapper.PairMapper;
import ru.company.understandablepractice.model.User;
import ru.company.understandablepractice.model.types.ClientStatus;
import ru.company.understandablepractice.service.PairService;

import java.time.LocalDate;

@Tag(
        name = "Пары",
        description = "Операции над Парами"
)
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/pair")
public class PairController {
    private final PairService service;

    private final PairMapper mapper;

    private final HttpServletRequestService requestService;

    @Operation(summary = "Получение по ID", description = "Позволяет получить пару по ключу")
    @GetMapping("/get/{id}")
    public ResponseEntity<PairResponse> getById(@PathVariable(name = "id") @Parameter(description = "ID пары") long id) {
        log.info("get pair by id {}", id);
        return service.getById(id)
                .map(value -> new ResponseEntity<>(mapper.fromEntityToResponse(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Обновление", description = "Позволяет обновить данные пары")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Parameter(description = "Пара") PairResponse response) {
        log.info("update pair {}", response);
        ResponseEntity<Long> responseEntity;
        try {
            var entity = mapper.fromResponseToEntity(response);
            var user = new User(requestService.getIdFromRequestToken());
            entity.setUser(user);
            responseEntity = service.create(entity)
                    .map(value -> new ResponseEntity<>(value.getId(), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @Operation(summary = "Создать", description = "Создать пару")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Parameter(description = "Пара") PairResponse response) {
        log.info("create pair {}", response);
        ResponseEntity<Long> responseEntity;
        try {
            var entity = mapper.fromResponseToEntity(response);
            var user = new User(requestService.getIdFromRequestToken());
            entity.setUser(user);
            entity.setClientStatus(ClientStatus.REQUEST);
            entity.setDateFirstRequest(LocalDate.now());
            responseEntity = service.create(entity)
                    .map(value -> new ResponseEntity<>(value.getId(), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @Operation(summary = "Удалить", description = "Удаление пары")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") @Parameter(description = "ID пары") long id) {
        log.info("delete pair by id {}", id);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
