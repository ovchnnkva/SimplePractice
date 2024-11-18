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
public class Child extends Customer{
    @Convert(converter = BringsClientConverter.class)
    @Column(name = "brings_client")
    private BringsClient bringsClient;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_parent_id")
    private Person firstParent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_parent_id")
    private Person secondParent;

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

    public Child(Customer customer) {

        this.id = customer.getId();
        this.clientType = ClientType.CHILD;
        this.fullName = customer.getFullName();
        this.firstName = customer.getFirstName();
        this.secondName = customer.getSecondName();
        this.lastName = customer.getLastName();
        this.birth = customer.getBirth();
        this.phoneNumber = customer.getPhoneNumber();
        this.mail = customer.getMail();
        this.gender = customer.getGender();
        this.clientStatus = customer.getClientStatus();
        this.meetingFormat = customer.getMeetingFormat();
        this.setCustomerCredentials(new CustomerCredentials(Set.of(new Role(4, "ROLE_CHILD"))));
        this.setApplicationFormStatus(customer.getApplicationFormStatus());
    }

    public Child() {
        this.setCustomerCredentials(new CustomerCredentials(Set.of(new Role(4, "ROLE_CHILD"))));
        this.setApplicationFormStatus(ApplicationFormStatus.NOT_CREATED);
    }
}
