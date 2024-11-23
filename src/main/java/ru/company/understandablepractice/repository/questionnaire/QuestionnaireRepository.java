package ru.company.understandablepractice.repository.questionnaire;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.company.understandablepractice.model.questionnaire.Questionnaire;

import java.util.Set;

@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {

    @Query(value =
            "SELECT q.id, q.title, q.dateCreated, q.isTest " +
                    "FROM Questionnaire q " +
                    "WHERE q.user.id = :userId " +
                    "ORDER BY q.dateCreated DESC " +
                    "OFFSET :offset ROWS " +
                    "FETCH NEXT :limit ROWS ONLY"
    )
    Set<Questionnaire> findAllByUserId(@Param("userId") long userId, @Param("offset") long offset, @Param("limit") long limit);

}
