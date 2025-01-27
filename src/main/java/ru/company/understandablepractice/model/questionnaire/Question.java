package ru.company.understandablepractice.model.questionnaire;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.company.understandablepractice.model.types.QuestionType;
import ru.company.understandablepractice.model.types.converters.QuestionTypeConverter;

import java.util.List;
import java.util.Objects;
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

    @Column(name = "question_order")
    private int order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionnaire_id")
    private Questionnaire questionnaire;

    @Convert(converter = QuestionTypeConverter.class)
    @Column(name = "question_type")
    private QuestionType type;

    @Column(name = "question_text", columnDefinition = "TEXT")
    private String text;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerOption> answerOptions;

    public Question(long id) {
        this.id = id;
    }

    public void setAnswerOptions(List<AnswerOption> answerOptions) {
        answerOptions.forEach(answerOption -> answerOption.setQuestion(this));
        if(this.answerOptions == null) {
            this.answerOptions = answerOptions;
        } else {
            this.answerOptions.clear();
            this.answerOptions.addAll(answerOptions);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id == question.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
