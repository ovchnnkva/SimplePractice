package ru.company.understandablepractice.repository.questionnaire;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.company.understandablepractice.model.questionnaire.ClientResult;

import java.util.List;

@Repository
public interface ClientResultRepository extends JpaRepository<ClientResult, Long> {
    List<ClientResult> findByCustomer_idAndQuestionnaire_User_id(PageRequest pageRequest, Long customerId, Long userId);
    ClientResult findByCustomer_idAndQuestionnaire_id(Long customerId, Long questionnaireId);

    @Query("SELECT COUNT(cr) FROM ClientResult cr WHERE cr.customer.id = :customerId")
    int countByCustomerIdAndUserId(long customerId);
}
