package ru.company.understandablepractice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.company.understandablepractice.model.types.BringsClient;
import ru.company.understandablepractice.model.types.converters.BringsClientConverter;

@Getter
@Setter
@Entity
@Table(name = "child")
public class Child extends Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_id")
    private long id;

    @Convert(converter = BringsClientConverter.class)
    @Column(name = "brings_client")
    private BringsClient bringsClient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer firstParent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer secondParent;

    @Column(name = "payer_full_name")
    private String payerFullName;

    @Column(name = "adult_request_for_therapy_reason")
    private String adultRequestForTherapyReason;

    @Column(name = "adult_request_for_therapy_desired_outcome")
    private String adultRequestForTherapyDesiredOutcome;

    @Column(name = "child_explanation_for_seeing_psychologist")
    private String childExplanationForSeeingPsychologist;

    @Column(name = "child_desired_changes")
    private String childDesiredChanges;
}
