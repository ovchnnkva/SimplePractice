package ru.company.understandablepractice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.company.understandablepractice.dto.ChildResponse;
import ru.company.understandablepractice.dto.mapper.ChildMapper;
import ru.company.understandablepractice.security.services.ApplicationFormService;
import ru.company.understandablepractice.service.ChildService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/applicationForm")
@Tag(name = "Ссылка на анкету")
public class ApplicationFormController {
    private final ApplicationFormService applicationFormService;
    private final ChildService childService;
    private final ChildMapper childMapper;

    @Operation(summary = "Ссылка на анкету", description = """
    Вид значения - clientType/token
    Если статус токена = не создан, то генерирует новый. Если создан, возвращает созданный токен.
    Если токен уже создавался и был использован - 404 ошибка
    """)
    @GetMapping("get/{id}")
    public ResponseEntity<String> getLink(@PathVariable(name = "id") long id) {
        log.info("get application form token for person with id {}", id);
        return applicationFormService.createLink(id)
                .map(token -> new ResponseEntity<>(token, HttpStatus.CREATED))
                .orElseGet(() -> applicationFormService.getLink(id)
                        .map(token -> new ResponseEntity<>(token, HttpStatus.OK))
                        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND))
                );
    }

    @GetMapping("/get/CHILD/{token}")
    public ResponseEntity<ChildResponse> getChildForm(@PathVariable(name = "token") String token) {
        long id = applicationFormService.getPersonId(token);
        log.info("get child by id {}", id);

        return childService.getById(id)
                .map(value -> new ResponseEntity<>(childMapper.fromEntityToResponse(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
