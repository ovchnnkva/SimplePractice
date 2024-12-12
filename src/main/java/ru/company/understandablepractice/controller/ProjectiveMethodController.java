package ru.company.understandablepractice.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.understandablepractice.dto.mapper.PhotoProjectiveMethodMapper;
import ru.company.understandablepractice.dto.mapper.ProjectiveMethodMapper;
import ru.company.understandablepractice.dto.projectivemethod.PhotoProjectiveMethodResponse;
import ru.company.understandablepractice.dto.projectivemethod.ProjectiveMethodResponse;
import ru.company.understandablepractice.model.PhotoProjectiveMethod;
import ru.company.understandablepractice.model.ProjectiveMethod;
import ru.company.understandablepractice.service.PhotoProjectiveMethodService;
import ru.company.understandablepractice.service.ProjectiveMethodService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Tag(
        name = "Проективные методики"
)
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/innerOptions/projectiveMethods")
public class ProjectiveMethodController {

    private final ProjectiveMethodService projectiveMethodService;
    private final PhotoProjectiveMethodService photoProjectiveMethodService;

    private final ProjectiveMethodMapper projectiveMethodMapper;
    private final PhotoProjectiveMethodMapper photoProjectiveMethodMapper;

    @Operation(summary = "Получение по ID", description = "Позволяет получить методику по ключу")

    @GetMapping("/get/{id}")
    public ResponseEntity<ProjectiveMethodResponse> getById(@PathVariable(name = "id") long id) {
        log.info("get Projective Method by id {}", id);
        return projectiveMethodService.getById(id)
                .map(value -> new ResponseEntity<>(projectiveMethodMapper.fromEntityToResponse(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Обновление", description = "Позволяет обновить данные методики")

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ProjectiveMethodResponse response) {
        log.info("update Projective Method {}", response);
        ResponseEntity<Long> responseEntity;
        try {
            var entity = projectiveMethodMapper.fromResponseToEntity(response);
            responseEntity = projectiveMethodService.create(entity)
                    .map(value -> new ResponseEntity<>(value.getId(), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @Operation(summary = "Создать", description = "Создать методику")

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProjectiveMethodResponse response) {
        log.info("create Projective Method {}", response);
        ResponseEntity<Long> responseEntity;
        try {
            var entity = projectiveMethodMapper.fromResponseToEntity(response);
            entity.setDateCreateMethod(LocalDate.now());
            responseEntity = projectiveMethodService.create(entity)
                    .map(value -> new ResponseEntity<>(value.getId(), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error(e.getLocalizedMessage());
        }

        return responseEntity;
    }

    @Operation(summary = "Удалить", description = "Удаление методики")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
        log.info("delete Projective Method by id {}", id);
        projectiveMethodService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Проективные методики для встречи", description = "Позволяет получить все проективные методики по id встречи")
    @GetMapping("byMeet/{meetId}")
    public ResponseEntity<List<ProjectiveMethodResponse>> getProjectiveMethods(@PathVariable @Parameter(description = "meetId") long meetId) {
        List<ProjectiveMethod> result = projectiveMethodService.findProjectiveMethodByMeetId(meetId);
        return result.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(result.stream()
                .map(projectiveMethodMapper::fromEntityToResponse)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @Operation(summary = "Проективные методики по клиенту", description = "Позволяет получить все проективные методики по id клиента")
    @GetMapping("byCustomer/{customerId}")
    public ResponseEntity<List<ProjectiveMethodResponse>> getProjectiveMethodsByCustomer(@PathVariable @Parameter(description = "customerId") long customerId) {
        List<ProjectiveMethod> result = projectiveMethodService.findProjectiveMethodByCustomerId(customerId);
        return result.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(result.stream()
                .map(projectiveMethodMapper::fromEntityToResponse)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @Operation(summary = "Все фото по типу методики", description = "Позволяет получить все фото методик по id типа методики")
    @GetMapping("getAllPhotos/{typeMethodId}")
    public ResponseEntity<List<PhotoProjectiveMethodResponse>> getAllPhotos(@PathVariable @Parameter(description = "typeMethodId") long typeMethodId) {
        List<PhotoProjectiveMethod> result = photoProjectiveMethodService.findPhotosByMethodType(typeMethodId);
        return result.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(result.stream()
                .map(photoProjectiveMethodMapper::fromEntityToResponse)
                .collect(Collectors.toList()), HttpStatus.OK);
    }
}
