package ru.company.understandablepractice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.company.understandablepractice.model.types.ApplicationFormStatus;
import ru.company.understandablepractice.model.types.ClientType;
import ru.company.understandablepractice.model.types.FamilyStatus;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "pair")
public class Pair extends Person{
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_customer_id")
    private Customer secondCustomer;

    @Column(name = "client_request_for_therapy_reason", columnDefinition = "TEXT")
    private String clientFirstRequestTherapyReason;

    @Column(name = "client_request_for_therapy_desired_outcome", columnDefinition = "TEXT")
    private String clientFirstRequestTherapyDesiredOutcome;

    @Column(name = "client2_request_for_therapy_reason", columnDefinition = "TEXT")
    private String secondClientRequestTherapyReason;

    @Column(name = "client2_request_for_therapy_desired_outcome", columnDefinition = "TEXT")
    private String clientSecondRequestTherapyDesiredOutcome;

    @Column(name = "family_status")
    private FamilyStatus familyStatus;

    @Column(name = "full_name_cotherapy")
    private String fullNameCotherapy;

    @Column(name = "phone_number_cotherapy", length = 20)
    private String phoneNumberCotherapy;

    @Column(name = "mail_cotherapy")
    private String mailCotherapy;

    @Column(name = "financial_terms_between_cotherapists")
    private String financialTermsCotherapists;

    public Pair(Person person) {

        this.id = person.getId();
        this.clientType = ClientType.PAIR;
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
        this.setPersonCredentials(new PersonCredentials(Set.of(new Role(5, "ROLE_PAIR"))));
        this.setApplicationFormStatus(person.getApplicationFormStatus());
    }

    public Pair() {
        this.setPersonCredentials(new PersonCredentials(Set.of(new Role(5, "ROLE_PAIR"))));
        this.setApplicationFormStatus(ApplicationFormStatus.NOT_CREATED);
    }
}
