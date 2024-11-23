package ru.company.understandablepractice.service;

import org.springframework.stereotype.Service;
import ru.company.understandablepractice.controller.HttpServletRequestService;
import ru.company.understandablepractice.model.questionnaire.ClientResult;
import ru.company.understandablepractice.model.questionnaire.Questionnaire;
import ru.company.understandablepractice.repository.questionnaire.ClientResultRepository;
import ru.company.understandablepractice.repository.questionnaire.QuestionnaireRepository;

import java.util.Set;

@Service
public class QuestionnaireService extends CRUDService<Questionnaire>{
    private final QuestionnaireRepository repository;
    private final ClientResultRepository clientResultRepository;
    private final HttpServletRequestService requestService;

    QuestionnaireService(QuestionnaireRepository repository,
                         ClientResultRepository clientResultRepository,
                         HttpServletRequestService requestService) {
        super(repository);
        this.repository = repository;
        this.clientResultRepository = clientResultRepository;
        this.requestService = requestService;
    }

    public Set<Questionnaire> getAllByUser(long offset, long limit) {
        long userId = requestService.getIdFromRequestToken();
        return repository.findAllByUserId(userId, offset, limit);
    }

    public Set<ClientResult> getAllByCustomer(long customerId, long offset, long limit) {
        long userId = requestService.getIdFromRequestToken();
        return clientResultRepository.findAllByCustomerId(customerId, userId, offset, limit);
    }
}
