package ru.company.understandablepractice.model.questionnaire;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "answer_options")
@NoArgsConstructor
@AllArgsConstructor
public class AnswerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_option_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "option_text", columnDefinition = "TEXT")
    private String text;

    @Column(name = "is_correct")
    private boolean isCorrect;

    public AnswerOption(long id) {
        this.id = id;
    }
}
