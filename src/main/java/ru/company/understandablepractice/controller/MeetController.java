package ru.company.understandablepractice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.understandablepractice.dto.CustomerMeetInfoResponse;
import ru.company.understandablepractice.dto.MeetResponse;
import ru.company.understandablepractice.dto.mapper.MeetMapper;
import ru.company.understandablepractice.model.User;
import ru.company.understandablepractice.service.MeetService;

@Tag(
        name = "Встреча",
        description = "Операции над Встречами"
)
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/meet")
public class MeetController {
    private final MeetService service;

    private final MeetMapper mapper;

    private final HttpServletRequestService requestService;

    @Operation(summary = "Получение по ID", description = "Позволяет получить встречу по ключу")
    @GetMapping("/get/{id}")
    public ResponseEntity<MeetResponse> getById(@PathVariable(name = "id") @Parameter(description = "ID встречи") long id) {
        log.info("get meet by id {}", id);
        return service.getById(id)
                .map(value -> new ResponseEntity<>(mapper.fromEntityToResponse(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Обновление", description = "Позволяет обновить данные встречи")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Parameter(description = "Встреча") MeetResponse response) {
        log.info("update meet {}", response);
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

    @Operation(summary = "Создать", description = "Создать встречу")
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Parameter(description = "Встреча") MeetResponse response) {
        log.info("create meet {}", response);

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
            log.error(e.getLocalizedMessage());
        }

        return responseEntity;
    }

    @Operation(summary = "Удалить", description = "Удаление встречи")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") @Parameter(description = "ID встречи") long id) {
        log.info("delete meet by id {}", id);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Инормация по встречам клиента", description = "Позволяет получить инфу о кол-ве/последней/следующей встречах")
    @GetMapping("/customerInfo/{customerId}")
    public ResponseEntity<CustomerMeetInfoResponse> getCustomerMeetInfo(@PathVariable(name = "customerId") @Parameter(description = "ID клиента") long customerId) {
        return service.getCustomerMeetInfo(customerId)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
