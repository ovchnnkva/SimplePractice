package ru.company.understandablepractice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.understandablepractice.dto.ChildResponse;
import ru.company.understandablepractice.dto.mapper.ChildMapper;
import ru.company.understandablepractice.service.ChildService;

@Tag(
        name = "Ребенок",
        description = "Операции над карточкой ребенка"
)
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/child")
public class ChildController {
    private final ChildService service;

    private final ChildMapper mapper;

    @Operation(summary = "Получение по ID", description = "Позволяет получить информацию о ребенке по ключу")
    @GetMapping("/{id}")
    public ResponseEntity<ChildResponse> getById(@PathVariable @Parameter(description = "ID ребенка") long id){
        log.info("get child by id {}", id);

        return service.getById(id)
                .map(value -> new ResponseEntity<>(mapper.fromEntityToResponse(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Обновление", description = "Позволяет обновить данные о ребенке")
    @PutMapping
    public ResponseEntity<?> update(@RequestParam @Parameter(description = "Ребенок") ChildResponse response){
        return service.create(mapper.fromResponseToEntity(response))
                .map(value -> new ResponseEntity<>(HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Operation(summary = "Создать", description = "Добавить информацию о ребенке")
    @PostMapping
    public ResponseEntity<Long> create(@RequestParam @Parameter(description = "Ребенок") ChildResponse response){
        return service.create(mapper.fromResponseToEntity(response))
                .map(value -> new ResponseEntity<>(value.getId(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Operation(summary = "Удалить", description = "Удалить информацию о ребенке")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @Parameter(description = "Ребенок") long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
