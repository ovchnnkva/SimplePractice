package ru.company.understandablepractice.model.questionnaire;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "text")
    private String text;

    @Column(name = "is_correct")
    private boolean isCorrect;

    @OneToMany(mappedBy = "answerOption", fetch = FetchType.EAGER)
    private List<ClientChoice> clientChoices;

    @Column(name = "is_archive_value")
    private boolean isArchiveValue = false;

    public AnswerOption(long id) {
        this.id = id;
    }

    public void setClientChoices(List<ClientChoice> clientChoices) {
        clientChoices.forEach(choice -> choice.setAnswerOption(this));
        this.clientChoices = clientChoices;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AnswerOption that = (AnswerOption) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
