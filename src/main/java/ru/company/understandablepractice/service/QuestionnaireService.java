package ru.company.understandablepractice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.controller.HttpServletRequestService;
import ru.company.understandablepractice.dto.mapper.questionnaire.ClientResultMapper;
import ru.company.understandablepractice.dto.mapper.questionnaire.QuestionnaireMapper;
import ru.company.understandablepractice.dto.questionnaire.AnswerOptionResponse;
import ru.company.understandablepractice.dto.questionnaire.ClientResultResponse;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.User;
import ru.company.understandablepractice.model.questionnaire.ClientChoice;
import ru.company.understandablepractice.model.questionnaire.ClientResult;
import ru.company.understandablepractice.model.questionnaire.Questionnaire;
import ru.company.understandablepractice.repository.CustomerRepository;
import ru.company.understandablepractice.repository.questionnaire.ClientResultRepository;
import ru.company.understandablepractice.repository.questionnaire.QuestionnaireRepository;
import ru.company.understandablepractice.security.services.JwtService;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuestionnaireService extends CRUDService<Questionnaire> {
    private final QuestionnaireRepository repository;
    private final ClientResultRepository clientResultRepository;
    private final HttpServletRequestService requestService;
    private final CustomerRepository customerRepository;
    private final JwtService jwtService;

    private final QuestionnaireMapper questionnaireMapper;
    private final ClientResultMapper clientResultMapper;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    QuestionnaireService(QuestionnaireRepository repository,
                         ClientResultRepository clientResultRepository,
                         HttpServletRequestService requestService,
                         QuestionnaireMapper questionnaireMapper,
                         ClientResultMapper clientResultMapper,
                         CustomerRepository customerRepository,
                         JwtService jwtService) {
        super(repository);
        this.repository = repository;
        this.clientResultRepository = clientResultRepository;
        this.requestService = requestService;
        this.questionnaireMapper = questionnaireMapper;
        this.clientResultMapper = clientResultMapper;
        this.customerRepository = customerRepository;
        this.jwtService = jwtService;
    }

    @Override
    public Optional<Questionnaire> create(Questionnaire entity) throws Exception {
        entity.setUser(new User(requestService.getIdFromRequestToken()));
        return super.create(entity);
    }

    public Set<Questionnaire> getAllByUser(long offset, long limit) {
        long userId = requestService.getIdFromRequestToken();
        return repository.findAllByUserId(userId, offset, limit);
    }

    public Set<ClientResult> getAllByCustomer(long customerId, long offset, long limit) {
        long userId = requestService.getIdFromRequestToken();
        return clientResultRepository.findAllByCustomerId(customerId, userId, offset, limit);
    }

    public Optional<ClientResult> createClientResult(ClientResult entity) {
        return Optional.of(clientResultRepository.save(entity));
    }

    public Optional<ClientResultResponse> getClientResultById(long id) {
        Optional<ClientResult> clientResult = clientResultRepository.findById(id);
        ClientResultResponse response = null;
        if (clientResult.isPresent()) {
            response = clientResultMapper.fromEntityToResponse(clientResult.get());

            Set<Long> clientChoiceIds = getClientChoicesIds(clientResult.get().getClientChoices());

            response.getQuestionnaire()
                    .getQuestions()
                    .forEach(questionResponse -> setIsChoice(questionResponse.getAnswerOptions(), clientChoiceIds));

        }
        return Optional.ofNullable(response);
    }

    public Optional<String> createLink(long questionnaireId, long customerId) {
        Customer customer = customerRepository.findCustomerById(customerId).orElseThrow();

        setCredentials(customer);

        String link = String.format(
                "%s/%s",
                questionnaireId,
                jwtService.generatePersonToken(customer.getCustomerCredentials())
        );
        customer.setApplicationFormToken(link);
        log.info("create person link {}", link);
        customerRepository.save(customer);

        return Optional.of(link);
    }

    private void setCredentials(Customer customer) {
        customer.getCustomerCredentials().setUsername(customer.getMail());
        customer.getCustomerCredentials().setPassword(encoder.encode(String.valueOf(customer.hashCode())));
    }

    private Set<Long> getClientChoicesIds(Set<ClientChoice> clientChoices) {
        return clientChoices.stream()
                .map(clientChoice -> clientChoice.getAnswerOption().getId())
                .collect(Collectors.toSet());
    }

    private void setIsChoice(Set<AnswerOptionResponse> answerOptionResponses, Set<Long> clientChoiceIds) {
        answerOptionResponses.forEach(answerOptionResponse ->
                answerOptionResponse.setChoice(clientChoiceIds.contains(answerOptionResponse.getId()))
        );
    }
}
