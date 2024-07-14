package ru.company.understandablepractice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.company.understandablepractice.model.types.FamilyStatus;

@Getter
@Setter
@Entity
@Table(name = "pair")
public class Pair extends Person{
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_customer_id")
    private Customer firstCustomer;

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
}
