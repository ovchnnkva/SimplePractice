package ru.company.understandablepractice.repository.questionnaire;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.company.understandablepractice.model.questionnaire.ClientResult;

import java.util.List;

@Repository
public interface ClientResultRepository extends JpaRepository<ClientResult, Long> {
    List<ClientResult> findByCustomer_idAndQuestionnaire_User_id(PageRequest pageRequest, Long customerId, Long userId);
}
