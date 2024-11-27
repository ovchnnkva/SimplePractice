package ru.company.understandablepractice.model.questionnaire;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.service.QuestionnaireService;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "client_result")
@NoArgsConstructor
public class ClientResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionnaire_id")
    private Questionnaire questionnaire;

    @Column(name = "date_result")
    private LocalDate dateResult;

    @OneToMany(mappedBy = "clientResult", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ClientChoice> clientChoices;

    public ClientResult(long id, LocalDate dateResult, Questionnaire questionnaire) {
        this.id = id;
        this.dateResult = dateResult;
        this.questionnaire = questionnaire;
    }
    public void setClientChoices(Set<ClientChoice> clientChoices) {
        clientChoices.forEach(choice -> choice.setClientResult(this));
        this.clientChoices = clientChoices;
    }
}
