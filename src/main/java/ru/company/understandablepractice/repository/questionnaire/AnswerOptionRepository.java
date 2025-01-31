package ru.company.understandablepractice.repository.questionnaire;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.company.understandablepractice.model.questionnaire.AnswerOption;

public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Long> {
}
