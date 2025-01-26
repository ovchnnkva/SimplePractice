package ru.company.understandablepractice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.understandablepractice.dto.customers.*;
import ru.company.understandablepractice.dto.mapper.ChildMapper;
import ru.company.understandablepractice.dto.mapper.CustomerMapper;
import ru.company.understandablepractice.dto.mapper.PairMapper;
import ru.company.understandablepractice.security.services.ApplicationFormService;
import ru.company.understandablepractice.service.ChildService;
import ru.company.understandablepractice.service.CustomerService;
import ru.company.understandablepractice.service.PairService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/applicationForm")
@Tag(name = "Ссылка на анкету")
public class ApplicationFormController {
    private final ApplicationFormService applicationFormService;
    private final ChildService childService;
    private final ChildMapper childMapper;

    private final PairService pairService;
    private final PairMapper pairMapper;

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

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

    @GetMapping("get/CHILD")
    public ResponseEntity<ChildApplicationDto> getChildForm() {
        long id = applicationFormService.getPersonId();
        log.info("get child by id {}", id);

        return childService.getById(id)
                .map(value -> new ResponseEntity<>(childMapper.fromEntityToApplicationDto(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/get/PAIR")
    public ResponseEntity<PairResponse> getPairForm() {
        long id = applicationFormService.getPersonId();
        log.info("get pair by id {}", id);

        return pairService.getById(id)
                .map(value -> new ResponseEntity<>(pairMapper.fromEntityToResponse(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/get/CUSTOMER")
    public ResponseEntity<CustomerApplicationDto> getCustomerForm() {
        long id = applicationFormService.getPersonId();
        log.info("get pair by id {}", id);

        return customerService.getById(id)
                .map(value -> new ResponseEntity<>(customerMapper.fromEntityToApplicationDto(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/CHILD")
    public ResponseEntity<?> updateChildForm(@RequestBody ChildResponse response) {
        long id = applicationFormService.getPersonId();
        log.info("update child by id {}", id);

        return new ResponseEntity<>(applicationFormService.update(response, id), HttpStatus.OK);
    }

    @PutMapping("/update/PAIR")
    public ResponseEntity<?> updatePairForm(@RequestBody PairResponse response) {
        long id = applicationFormService.getPersonId();
        log.info("update pair by id {}", id);

        return new ResponseEntity<>(applicationFormService.update(response, id), HttpStatus.OK);
    }

    @PutMapping("/update/CUSTOMER")
    public ResponseEntity<?> updateCustomerForm(@RequestBody CustomerResponse response) {
        long id = applicationFormService.getPersonId();
        log.info("update customer by id {}", id);

        return new ResponseEntity<>(applicationFormService.update(response, id), HttpStatus.OK);
    }
}
