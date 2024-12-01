package ru.company.understandablepractice.controller.questionnaire;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.understandablepractice.controller.HttpServletRequestService;
import ru.company.understandablepractice.dto.mapper.questionnaire.ClientResultMapper;
import ru.company.understandablepractice.dto.mapper.questionnaire.QuestionnaireMapper;
import ru.company.understandablepractice.dto.questionnaire.*;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.User;
import ru.company.understandablepractice.model.questionnaire.ClientResult;
import ru.company.understandablepractice.model.questionnaire.Questionnaire;
import ru.company.understandablepractice.security.JwtType;
import ru.company.understandablepractice.security.services.JwtService;
import ru.company.understandablepractice.service.QuestionnaireService;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/questionnaire")
@Tag(name = "Опросники и тесты")
public class QuestionnaireController {

    private final QuestionnaireService service;
    private final QuestionnaireMapper questionnaireMapper;
    private final ClientResultMapper clientResultMapper;
    private final JwtService jwtService;
    private final HttpServletRequest request;
    @Operation(summary = "Создание опросника",
            description = """
                    questions - список вопросов. каждый вопрос содержит answerOptions - список вариантов ответов.
                    Если тип вопроса = Cвободный ответ, то список answerOptions должен быть пустой.
                    Если запрос пришел с заполненным ид, то будет обновлен существующий опросник.
                    """)
    @PostMapping("/create")
    public ResponseEntity<Long> create(@RequestBody QuestionnaireDto request) {
        log.info("create questionnaire {}", request);
        ResponseEntity<Long> responseEntity;
        try {
            var entity = questionnaireMapper.fromRequestToEntity(request);
            responseEntity = service.create(entity)
                    .map(value -> new ResponseEntity<>(value.getId(), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error(e.getLocalizedMessage());
        }

        return responseEntity;
    }

    @Operation(summary = "Получить опросник/тест")
    @GetMapping("/get/{id}")
    public ResponseEntity<QuestionnaireDto> getById(@PathVariable("id") @Parameter(description = "id опросника/теста") long id) {
        long customerId = jwtService.extractUserId(request.getHeader("Authorization"), JwtType.ACCESS);
        log.info("get customer by id {}", customerId);
        log.info("get questionnaire {}", id);
        return service.getById(id)
                .map(result -> new ResponseEntity<>(questionnaireMapper.fromEntityToDto(result), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Удалить опросник")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        log.info("delete questionnaire {}", id);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Список тестов и опросников терапевта",
            description = "получить все встречи, созданные терапевтом. +пагинация")
    @GetMapping("/get/byUser/{offset}/{limit}")
    public ResponseEntity<Set<QuestionnaireMinResponse>> getAllByUser(@PathVariable("offset") long offset, @PathVariable("limit") long limit) {
        log.info("get all questionnaire by user id");
        Set<Questionnaire> result = service.getAllByUser(offset, limit);
        return result.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(result.stream()
                .map(questionnaireMapper::fromEntityToMinResponse)
                .collect(Collectors.toSet()), HttpStatus.OK);
    }

    @Operation(summary = "Список всех пройденных тестов клиента",
            description = "получить все тесты, которые прошел клиент. +пагинация")
    @GetMapping("get/byCustomer/{id}/{offset}/{limit}")
    public ResponseEntity<Set<ClientResultMinResponse>> getAllByCustomer(@PathVariable("id") @Parameter(description = "id клиента") long customerId, @PathVariable("offset") long offset, @PathVariable("limit") long limit) {
        log.info("get all by customer id {}", customerId);
        Set<ClientResult> result = service.getAllByCustomer(customerId, offset, limit);
        return result.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(result.stream()
                .map(clientResultMapper::fromEntityToMinResponse)
                .collect(Collectors.toSet()), HttpStatus.OK);
    }

    @Operation(summary = "Сохранение результатов теста")
    @PostMapping("create/result")
    public ResponseEntity<Long> createResult(@RequestBody ClientResultRequest request) {
        log.info("create result {}", request);
        ResponseEntity<Long> responseEntity;
        try {
            var entity = clientResultMapper.fromRequestToEntity(request);
            entity.setCustomer(new Customer(service.getPersonId()));
            responseEntity = service.createClientResult(entity)
                    .map(value -> new ResponseEntity<>(value.getId(), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error(e.getLocalizedMessage());
        }

        return responseEntity;
    }

    @Operation(summary = "Получить результаты теста",
            description = "получение результатов теста +информации о самом тесте по ид результата")
    @GetMapping("get/result/{id}")
    public ResponseEntity<ClientResultResponse> getResult(@PathVariable("id") long id) {
        log.info("get result {}", id);
        return service.getClientResultById(id)
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Получить ссылку на тест",
            description = "ссылка должна формироваться для конкретного клиента")
    @GetMapping("get/link/{customerId}/{questionnaireId}")
    public ResponseEntity<String> getLink(@PathVariable("customerId") long customerId, @PathVariable("questionnaireId") long questionnaireId) {
        log.info("get questionnaire {} link for customer {}", questionnaireId, customerId);
        return service.createLink(questionnaireId, customerId)
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
