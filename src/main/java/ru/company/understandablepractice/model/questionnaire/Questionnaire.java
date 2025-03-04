package ru.company.understandablepractice.model.questionnaire;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.company.understandablepractice.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "questionnaires")
@NoArgsConstructor
@ToString
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

    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    @Column(name = "is_archive_value")
    private boolean isArchiveValue = false;

    public Questionnaire(long id) {
        this.id = id;
    }

    public Questionnaire(long id, String title, boolean isTest) {
        this.id = id;
        this.title = title;
        this.isTest = isTest;
    }

    public Questionnaire(long id, String title, LocalDate dateCreated, boolean isTest) {
        this.id = id;
        this.title = title;
        this.dateCreated = dateCreated;
        this.isTest = isTest;
    }

    public void setQuestions(List<Question> questions) {
        questions.forEach(question -> question.setQuestionnaire(this));
        if(this.questions == null) {
            this.questions = questions;
        } else {
            this.questions.clear();
            this.questions.addAll(questions);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Questionnaire that = (Questionnaire) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
