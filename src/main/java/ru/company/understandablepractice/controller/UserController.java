package ru.company.understandablepractice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.company.understandablepractice.dto.UserResponse;
import ru.company.understandablepractice.dto.mapper.UserMapper;
import ru.company.understandablepractice.model.User;
import ru.company.understandablepractice.service.UserService;

import java.util.Optional;

import static org.mapstruct.factory.Mappers.*;

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

    @Operation(summary = "Получение по ID", description = "Позволяет получить пользователя по ключу")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable @Parameter(description = "ID пользователя") long id) {
        log.info("get user by id " + id);

        Optional<User> user = service.getById(id);
        log.info(mapper.toResponse(user.get()).toString());
        return user
                .map(value -> new ResponseEntity<>(mapper.toResponse(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
