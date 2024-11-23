package ru.company.understandablepractice.model.questionnaire;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.company.understandablepractice.model.User;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "questionnaires")
@NoArgsConstructor
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "questionnaire_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title", columnDefinition = "TEXT")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_test")
    private boolean isTest;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL)
    private Set<Question> questions;

    public Questionnaire(long id) {
        this.id = id;
    }

    public void setQuestions(Set<Question> questions) {
        questions.forEach(question -> question.setQuestionnaire(this));
        this.questions = questions;
    }
}
