package ru.company.understandablepractice.model.questionnaire;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.company.understandablepractice.model.types.QuestionType;
import ru.company.understandablepractice.model.types.converters.QuestionTypeConverter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "questions")
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "questionnaire_id")
    private Questionnaire questionnaire;

    @Convert(converter = QuestionTypeConverter.class)
    @Column(name = "question_type")
    private QuestionType type;

    @Column(name = "question_text", columnDefinition = "TEXT")
    private String text;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private Set<AnswerOption> answerOptions;

    public Question(long id) {
        this.id = id;
    }

    public void setAnswerOptions(Set<AnswerOption> answerOptions) {
        answerOptions.forEach(answerOption -> answerOption.setQuestion(this));
        this.answerOptions = answerOptions;
    }
}
