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
    public Optional<Questionnaire> getById(long id) {
        long customerId = getPersonId();
        if(customerRepository.findById(customerId).isPresent()) {
            return clientResultRepository.findByCustomer_idAndQuestionnaire_id(customerId, id) != null ? Optional.empty() : repository.findById(id);
        }
        return repository.findById(id);
    }


    public QuestionnaireDto update(QuestionnaireDto response) {
        Optional<Questionnaire> entity = repository.findById(response.getId());

        entity.ifPresent(questionnaire -> {
            questionnaireMapper.updateEntityFromDto(response, questionnaire);
            questionnaire.setUser(new User(requestService.getIdFromRequestToken()));
            updateQuestions(response.getQuestions(), questionnaire.getQuestions());
            repository.save(questionnaire);
        });

        return response;
    }

    private void updateQuestions(List<QuestionDto> questionDtos, List<Question> questions) {
        if(questionDtos.isEmpty()) return;

        for(Question question : questions) {
            questionDtos.stream()
                    .filter(questionDto -> questionDto.getId() == question.getId()).findFirst()
                    .ifPresent(questionDto -> {
                        questionMapper.updateEntityFromDto(questionDto, question);
                        updateAnswerOptions(questionDto.getAnswerOptions(), question);
                    });
        }

        if(questionDtos.size() > questions.size()) {
            questions.addAll(
                    questionDtos.stream()
                            .filter(questionDto -> questionDto.getId() == 0)
                            .map(questionMapper::fromDtoToEntity)
                            .toList()
            );
        }
    }

    private void updateAnswerOptions(List<AnswerOptionDto> answerOptionDtos, Question question) {
        if(answerOptionDtos.isEmpty()) return;

        for (AnswerOption answerOption : question.getAnswerOptions()) {
            answerOptionDtos.stream()
                    .filter(answerOptionDto -> answerOptionDto.getId() == answerOption.getId()).findFirst()
                    .ifPresent(answerOptionDto -> answerOptionMapper.updateEntityFromDto(answerOptionDto, answerOption));
        }

        if(answerOptionDtos.size() > question.getAnswerOptions().size()) {
            question.getAnswerOptions().addAll(
                    answerOptionDtos.stream()
                            .filter(answerOptionDto -> answerOptionDto.getId() == 0)
                            .map(answerOptionDto -> answerOptionMapper.fromDtoToEntity(answerOptionDto, question.getId()))
                            .toList()
            );
        }
    }

    public QuestionnaireListMinResponse getAllByUser(int offset, int limit, Sort sort) {
        long userId = requestService.getIdFromRequestToken();

        return new QuestionnaireListMinResponse(repository.findByUser_id(PageRequest.of(offset, limit, sort), userId).stream()
                .map(questionnaireMapper::fromEntityToMinResponse)
                .collect(Collectors.toList()), repository.count());
    }

    public ClientResultListMinResponse getAllByCustomer(long customerId, int offset, int limit, Sort sort) {
        long userId = requestService.getIdFromRequestToken();
        List<ClientResult> result = clientResultRepository.findByCustomer_idAndQuestionnaire_User_id(PageRequest.of(offset, limit, sort), customerId, userId);
        return new ClientResultListMinResponse(
                result.stream().map(clientResultMapper::fromEntityToMinResponse).collect(Collectors.toList()),
                clientResultRepository.count());
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
            response = clientResultMapper.fromEntityToResponse(clientResult.get());

            List<Long> clientChoiceIds = getClientChoicesIds(clientResult.get().getClientChoices());

            response.getQuestionnaire()
                    .getQuestions()
                    .forEach(questionResponse -> setIsChoice(questionResponse.getAnswerOptions(), clientChoiceIds));

        }
        return Optional.ofNullable(response);
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

    public long getPersonId() {
        return jwtService.extractUserId(request.getHeader("Authorization"), JwtType.ACCESS);
    }

    public void deleteClientResult(long id) {
        clientResultRepository.deleteById(id);
    }
}
