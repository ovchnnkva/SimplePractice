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
    @Convert(converter = BringsClientConverter.class)
    @Column(name = "brings_client")
    private BringsClient bringsClient;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Customer firstParent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_parent_id")
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
