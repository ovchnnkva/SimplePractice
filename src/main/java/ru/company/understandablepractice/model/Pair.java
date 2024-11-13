package ru.company.understandablepractice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.company.understandablepractice.model.types.ApplicationFormStatus;
import ru.company.understandablepractice.model.types.ClientType;
import ru.company.understandablepractice.model.types.FamilyStatus;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "pair")
public class Pair extends Customer {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_person_id")
    private Person secondPerson;

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

    public Pair(Customer customer) {

        this.id = customer.getId();
        this.clientType = ClientType.PAIR;
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
        this.setCustomerCredentials(new CustomerCredentials(this, Set.of(new Role(5, "ROLE_PAIR"))));
        this.setApplicationFormStatus(customer.getApplicationFormStatus());
    }

    public Pair() {
        this.setCustomerCredentials(new CustomerCredentials(this, Set.of(new Role(5, "ROLE_PAIR"))));
        this.setApplicationFormStatus(ApplicationFormStatus.NOT_CREATED);
    }
}
