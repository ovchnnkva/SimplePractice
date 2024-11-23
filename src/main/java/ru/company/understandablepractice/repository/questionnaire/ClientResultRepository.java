package ru.company.understandablepractice.repository.questionnaire;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.company.understandablepractice.model.questionnaire.ClientResult;

import java.util.Set;

@Repository
public interface ClientResultRepository extends JpaRepository<ClientResult, Long> {
    @Query("SELECT r.id, r.questionnaire.title, r.dateResult, r.questionnaire.isTest " +
            "FROM ClientResult r " +
            "WHERE r.customer.id = :customerId " +
            "AND r.questionnaire.user.id = :userId " +
            "ORDER BY r.dateResult DESC " +
            "OFFSET :offset ROWS " +
            "FETCH NEXT :limit ROWS ONLY")
    Set<ClientResult> findAllByCustomerId(@Param("customerId") long customerId,
                                          @Param("userId") long userId,
                                          @Param("offset") long offset,
                                          @Param("limit") long limit);
}
