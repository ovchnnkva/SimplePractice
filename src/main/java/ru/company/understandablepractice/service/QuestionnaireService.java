package ru.company.understandablepractice.service;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.controller.HttpServletRequestService;
import ru.company.understandablepractice.dto.MeetResponse;
import ru.company.understandablepractice.dto.mapper.questionnaire.AnswerOptionMapper;
import ru.company.understandablepractice.dto.mapper.questionnaire.ClientResultMapper;
import ru.company.understandablepractice.dto.mapper.questionnaire.QuestionMapper;
import ru.company.understandablepractice.dto.mapper.questionnaire.QuestionnaireMapper;
import ru.company.understandablepractice.dto.questionnaire.*;
import ru.company.understandablepractice.model.*;
import ru.company.understandablepractice.model.questionnaire.*;
import ru.company.understandablepractice.model.types.QuestionType;
import ru.company.understandablepractice.repository.CustomerRepository;
import ru.company.understandablepractice.repository.questionnaire.AnswerOptionRepository;
import ru.company.understandablepractice.repository.questionnaire.ClientResultRepository;
import ru.company.understandablepractice.repository.questionnaire.QuestionRepository;
import ru.company.understandablepractice.repository.questionnaire.QuestionnaireRepository;
import ru.company.understandablepractice.security.JwtType;
import ru.company.understandablepractice.security.services.JwtService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuestionnaireService extends CRUDService<Questionnaire> {
    private final QuestionnaireRepository repository;
    private final ClientResultRepository clientResultRepository;
    private final HttpServletRequestService requestService;
    private final CustomerRepository customerRepository;
    private final JwtService jwtService;
    private final HttpServletRequest request;

    private final QuestionnaireMapper questionnaireMapper;
    private final ClientResultMapper clientResultMapper;
    private final QuestionMapper questionMapper;
    private final AnswerOptionMapper answerOptionMapper;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    QuestionnaireService(QuestionnaireRepository repository,
                         ClientResultRepository clientResultRepository,
                         HttpServletRequestService requestService,
                         QuestionnaireMapper questionnaireMapper,
                         ClientResultMapper clientResultMapper,
                         AnswerOptionMapper answerOptionMapper,
                         QuestionMapper questionMapper,
                         CustomerRepository customerRepository,
                         JwtService jwtService,
                         HttpServletRequest request) {
        super(repository);
        this.repository = repository;
        this.clientResultRepository = clientResultRepository;
        this.requestService = requestService;
        this.questionnaireMapper = questionnaireMapper;
        this.questionMapper = questionMapper;
        this.answerOptionMapper = answerOptionMapper;
        this.clientResultMapper = clientResultMapper;
        this.customerRepository = customerRepository;
        this.jwtService = jwtService;
        this.request = request;
    }

    @Override
    public Optional<Questionnaire> create(Questionnaire entity) throws Exception {
        entity.setUser(new User(requestService.getIdFromRequestToken()));
        return super.create(entity);
    }

    @Override
    public void delete(long id) {
        Optional<Questionnaire> questionnaire = repository.findById(id);
        if(questionnaire.isPresent()) {
            questionnaire.get().setArchiveValue(true);
            repository.save(questionnaire.get());
        }
    }

    @Override
    public Optional<Questionnaire> getById(long id) {
        Optional<Questionnaire> questionnaire = repository.findById(id);
        questionnaire.ifPresent(this::clearArchiveValues);

        return questionnaire;
    }

    private void clearArchiveValues(Questionnaire questionnaire) {
        questionnaire.getQuestions()
                .removeAll(questionnaire.getQuestions().stream()
                        .filter(Question::isArchiveValue)
                        .toList()
                );

        questionnaire.getQuestions()
                .forEach(question -> question.getAnswerOptions()
                        .removeAll(question.getAnswerOptions().stream()
                                .filter(AnswerOption::isArchiveValue)
                                .toList()
                        )
                );
    }

    public QuestionnaireDto update(QuestionnaireDto response) {
        Optional<Questionnaire> entity = repository.findById(response.getId());

        entity.ifPresent(questionnaire -> {
            questionnaireMapper.updateEntityFromDto(response, questionnaire);
            questionnaire.setUser(new User(requestService.getIdFromRequestToken()));
            updateQuestions(response.getQuestions(), questionnaire.getQuestions(), questionnaire.getId());
            repository.save(questionnaire);
        });

        return response;
    }

    private void updateQuestions(List<QuestionDto> questionDtos,
                                 List<Question> questions,
                                 long questionnaireId) {
        if(questionDtos.isEmpty()) return;

        for(Question question : questions) {
            questionDtos.stream()
                    .filter(questionDto -> questionDto.getId() == question.getId()).findFirst()
                    .ifPresentOrElse(questionDto -> {
                        questionMapper.updateEntityFromDto(questionDto, question);
                        updateAnswerOptions(questionDto.getAnswerOptions(), question);
                    }, () -> question.setArchiveValue(true));
        }

        questions.addAll(
                questionDtos.stream()
                        .filter(questionDto -> questionDto.getId() == 0)
                        .map(question -> questionMapper.fromDtoToEntity(question, questionnaireId))
                        .toList()
        );

    }

    private void updateAnswerOptions(List<AnswerOptionDto> answerOptionDtos, Question question) {
        if(answerOptionDtos.isEmpty()) return;

        for (AnswerOption answerOption : question.getAnswerOptions()) {
            answerOptionDtos.stream()
                    .filter(answerOptionDto -> answerOptionDto.getId() == answerOption.getId()).findFirst()
                    .ifPresentOrElse(
                            answerOptionDto -> answerOptionMapper.updateEntityFromDto(answerOptionDto, answerOption),
                            () -> answerOption.setArchiveValue(true)
                    );
        }

        question.getAnswerOptions().addAll(
                answerOptionDtos.stream()
                        .filter(answerOptionDto -> answerOptionDto.getId() == 0)
                        .map(answerOptionDto -> answerOptionMapper.fromDtoToEntity(answerOptionDto, question.getId()))
                        .toList()
        );
    }

    public QuestionnaireListMinResponse getAllByUser(int offset, int limit, Sort sort) {
        long userId = requestService.getIdFromRequestToken();

        return new QuestionnaireListMinResponse(
                repository.findByUser_idAndIsArchiveValue(PageRequest.of(offset, limit, sort), userId, false).stream()
                .map(questionnaireMapper::fromEntityToMinResponse)
                .collect(Collectors.toList()), repository.countByUserId(userId)
        );
    }

    public ClientResultListMinResponse getAllByCustomer(long customerId, int offset, int limit, Sort sort) {
        long userId = requestService.getIdFromRequestToken();
        List<ClientResult> result = clientResultRepository.findByCustomer_idAndQuestionnaire_User_id(PageRequest.of(offset, limit, sort), customerId, userId);
        return new ClientResultListMinResponse(
                result.stream().map(clientResultMapper::fromEntityToMinResponse).collect(Collectors.toList()),
                clientResultRepository.countByCustomerIdAndUserId(customerId));
    }

    public Optional<ClientResult> createClientResult(ClientResultRequest request) {
        var entity = clientResultMapper.fromRequestToEntity(request);

        entity.setCustomer(new Customer(getPersonId()));
        return Optional.of(clientResultRepository.save(entity));
    }

    public Optional<ClientResultResponse> getClientResultById(long id) {
        Optional<ClientResult> clientResult = clientResultRepository.findById(id);
        ClientResultResponse response = null;
        if (clientResult.isPresent()) {
            ClientResult result = clientResult.get();
            response = clientResultMapper.fromEntityToResponse(result);

            List<Long> clientChoiceIds = getClientChoicesIds(result.getClientChoices());
            response.getQuestionnaire()
                    .getQuestions()
                    .forEach(questionResponse -> {
                        setIsChoice(questionResponse.getAnswerOptions(), clientChoiceIds);
                        setAnswerOptionText(questionResponse, result.getClientChoices());
                    });
        }
        return Optional.ofNullable(response);
    }

    private List<Long> getClientChoicesIds(List<ClientChoice> clientChoices) {
        return clientChoices.stream()
                .map(clientChoice -> clientChoice.getAnswerOption().getId())
                .collect(Collectors.toList());
    }

    private void setIsChoice(List<AnswerOptionResponse> answerOptionResponses, List<Long> clientChoiceIds) {
        answerOptionResponses.forEach(answerOptionResponse ->
                answerOptionResponse.setChoice(clientChoiceIds.contains(answerOptionResponse.getId()))
        );
    }

    private void setAnswerOptionText(QuestionResponse response, List<ClientChoice> clientChoices) {
        if(!response.getType().equals(QuestionType.DETAILED.getKey()))
            return;

        response.getAnswerOptions()
                .forEach(answerOptionResponse ->
                        answerOptionResponse.setText(getAnswerOptionText(clientChoices, answerOptionResponse.getId()))
                );
    }

    private String getAnswerOptionText(List<ClientChoice> clientChoices, long answerOptionId) {
        final String[] text = {null};
        clientChoices.stream()
                .filter(clientChoice -> clientChoice.getAnswerOption().getId() == answerOptionId)
                .findFirst().ifPresent(result -> text[0] = result.getText());

        return text[0];
    }

    public Optional<String> createLink(long questionnaireId, long customerId) {
        Optional<Customer> customerOptional = customerRepository.findCustomerById(customerId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            setCredentials(customer);
            String token = jwtService.generatePersonToken(customer.getCustomerCredentials());
            String link = String.format(
                    "%s/%s",
                    questionnaireId,
                    token
            );
            customer.setApplicationFormToken(token);
            log.info("create person link {}", link);
            customerRepository.save(customer);

            return Optional.of(link);
        } else return Optional.empty();
    }

    private void setCredentials(Customer customer) {
        CustomerCredentials customerCredentials = customer.getCustomerCredentials();
        customerCredentials.setUsername(customer.getMail());
        customerCredentials.setPassword(encoder.encode(String.valueOf(customer.hashCode())));

        customerCredentials.setRoles(new HashSet<>(List.of(new Role(6, "ROLE_TEST"))));
    }



    public long getPersonId() {
        return jwtService.extractUserId(request.getHeader("Authorization"), JwtType.ACCESS);
    }

    public void deleteClientResult(long id) {
        clientResultRepository.deleteById(id);
    }
}
