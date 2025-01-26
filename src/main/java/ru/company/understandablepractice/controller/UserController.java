package ru.company.understandablepractice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.understandablepractice.dto.UserResponse;
import ru.company.understandablepractice.dto.mapper.UserMapper;
import ru.company.understandablepractice.security.JwtType;
import ru.company.understandablepractice.security.services.JwtService;
import ru.company.understandablepractice.service.UserService;

@Tag(
        name = "Пользователи",
        description = "Операции над Пользователями"
)
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    private final UserService service;
    private final UserMapper mapper;
    private final HttpServletRequest request;
    private final JwtService jwtService;


    @Operation(summary = "Получение пользователя", description = "Позволяет получить пользователя ")
    @GetMapping("/get")
    public ResponseEntity<UserResponse> getById() {
        Long id = jwtService.extractUserId(request.getHeader("Authorization"), JwtType.ACCESS);
        log.info("get user by id {}", id);
        return service.getById(id)
                .map(value -> new ResponseEntity<>(mapper.fromEntityToResponse(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Обновление", description = "Позволяет обновить данные пользователя")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Parameter(description = "Пользователь") UserResponse response) {
        log.info("update user {}", response);
        return new ResponseEntity<>(service.update(response), HttpStatus.OK);
    }

    @Operation(summary = "Создать", description = "Создать пользователя")
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Parameter(description = "Пользователь") UserResponse response) {
        log.info("create user {}", response);
        ResponseEntity<Long> responseEntity;
        try {
            responseEntity = service.create(mapper.fromResponseToEntity(response))
                    .map(value -> new ResponseEntity<>(value.getId(), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @Operation(summary = "Удалить", description = "Удаление пользователя")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") @Parameter(description = "ID пользователя") long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
