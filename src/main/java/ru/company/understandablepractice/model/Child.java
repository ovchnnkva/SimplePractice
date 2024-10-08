package ru.company.understandablepractice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.company.understandablepractice.model.types.ApplicationFormStatus;
import ru.company.understandablepractice.model.types.BringsClient;
import ru.company.understandablepractice.model.types.ClientType;
import ru.company.understandablepractice.model.types.converters.BringsClientConverter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "child")
public class Child extends Person{
    @Convert(converter = BringsClientConverter.class)
    @Column(name = "brings_client")
    private BringsClient bringsClient;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_parent_id")
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

    public Child(Person person) {

        this.id = person.getId();
        this.clientType = ClientType.CHILD;
        this.fullName = person.getFullName();
        this.firstName = person.getFirstName();
        this.secondName = person.getSecondName();
        this.lastName = person.getLastName();
        this.birth = person.getBirth();
        this.phoneNumber = person.getPhoneNumber();
        this.mail = person.getMail();
        this.gender = person.getGender();
        this.clientStatus = person.getClientStatus();
        this.meetingFormat = person.getMeetingFormat();
        this.setPersonCredentials(new PersonCredentials(Set.of(new Role(4, "ROLE_CHILD"))));
        this.setApplicationFormStatus(person.getApplicationFormStatus());
    }

    public Child() {
        this.setPersonCredentials(new PersonCredentials(Set.of(new Role(4, "ROLE_CHILD"))));
        this.setApplicationFormStatus(ApplicationFormStatus.NOT_CREATED);
    }
}
