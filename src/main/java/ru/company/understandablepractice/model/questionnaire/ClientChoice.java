package ru.company.understandablepractice.model.questionnaire;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "responses")
public class ClientChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "result_id")
    private ClientResult clientResult;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "answer_option_id")
    private AnswerOption answerOption;

    @Column(name = "option_text", columnDefinition = "TEXT")
    private String text;
}
