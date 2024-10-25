package ru.company.understandablepractice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.understandablepractice.dto.mapper.PhotoProjectiveMethodMapper;
import ru.company.understandablepractice.dto.projectivemethod.PhotoProjectiveMethodResponse;
import ru.company.understandablepractice.service.PhotoProjectiveMethodService;

@Tag(
        name = "Фото методик CRUD"
)
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/innerOptions/photoMethods")
public class PhotoProjectiveMethodController {

    private final PhotoProjectiveMethodService service;

    private final PhotoProjectiveMethodMapper mapper;

    @Operation(summary = "Получение по ID", description = "Позволяет получить фото по ключу")
    @GetMapping("/get/{id}")
    public ResponseEntity<PhotoProjectiveMethodResponse> getById(@PathVariable(name = "id") long id) {
        log.info("get Photo Projective Method by id {}", id);
        return service.getById(id)
                .map(value -> new ResponseEntity<>(mapper.fromEntityToResponse(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Обновление", description = "Позволяет обновить данные фото")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody PhotoProjectiveMethodResponse response) {
        log.info("update Photo Projective Method {}", response);
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

    @Operation(summary = "Создать", description = "Создать фото")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody PhotoProjectiveMethodResponse response) {
        log.info("create Photo Projective Method {}", response);
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

    @Operation(summary = "Удалить", description = "Удаление фото")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
        log.info("delete Photo Projective Method by id {}", id);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
