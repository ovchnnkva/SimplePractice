package ru.company.understandablepractice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.understandablepractice.dto.mapper.TypeMethodMapper;
import ru.company.understandablepractice.dto.projectivemethod.TypeMethodResponse;
import ru.company.understandablepractice.security.JwtType;
import ru.company.understandablepractice.security.services.JwtService;
import ru.company.understandablepractice.service.TypeMethodService;

import java.util.List;

@Tag(
        name = "Типы методик CRUD"
)
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/innerOptions/typeMethod")
public class TypeMethodController {

    private final TypeMethodService service;
    private final TypeMethodMapper mapper;
    private final HttpServletRequest request;
    private final JwtService jwtService;

    @Operation(summary = "Получение по ID", description = "Позволяет получить тип по ключу")
    @GetMapping("/get/{id}")
    public ResponseEntity<TypeMethodResponse> getById(@PathVariable(name = "id") long id) {
        log.info("get Type Method by id {}", id);
        return service.getById(id)
                .map(value -> new ResponseEntity<>(mapper.fromEntityToResponse(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Обновление", description = "Позволяет обновить данные типа")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody TypeMethodResponse response) {
        log.info("update Type Method {}", response);
        ResponseEntity<Long> responseEntity;
        try {
            var entity = mapper.fromResponseToEntity(response);
            responseEntity = service.create(entity)
                    .map(value -> new ResponseEntity<>(value.getId(), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @Operation(summary = "Создать", description = "Создать тип")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody TypeMethodResponse response) {
        log.info("create Type Method {}", response);
        ResponseEntity<Long> responseEntity;
        try {
            var entity = mapper.fromResponseToEntity(response);
            responseEntity = service.create(entity)
                    .map(value -> new ResponseEntity<>(value.getId(), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error(e.getLocalizedMessage());
        }

        return responseEntity;
    }

    @Operation(summary = "Удалить", description = "Удаление типа")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
        log.info("delete Type Method by id {}", id);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Получить все", description = "Получение всех типов, доступных пользователю")
    @GetMapping("/getAllTypes")
    public ResponseEntity<List<TypeMethodResponse>> getAll() {
        Long userId = jwtService.extractUserId(request.getHeader("Authorization"), JwtType.ACCESS);
        log.info("auth user {}", userId);
        try {
            return service.findAllByUserId(userId)
                    .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
