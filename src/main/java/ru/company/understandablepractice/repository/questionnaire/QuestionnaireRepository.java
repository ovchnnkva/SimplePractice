package ru.company.understandablepractice.repository.questionnaire;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.company.understandablepractice.model.CustomerCredentials;
import ru.company.understandablepractice.model.questionnaire.Questionnaire;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
    List<Questionnaire> findByUser_id(PageRequest pageRequest, Long id);

    @Query("SELECT COUNT(q) FROM Questionnaire q WHERE q.user.id = :userId")
    int countByUserId(long userId);
}
