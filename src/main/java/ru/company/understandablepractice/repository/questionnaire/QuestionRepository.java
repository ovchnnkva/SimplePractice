package ru.company.understandablepractice.repository.questionnaire;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.company.understandablepractice.model.questionnaire.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
