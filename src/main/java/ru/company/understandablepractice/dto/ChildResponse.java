package ru.company.understandablepractice.dto;

import lombok.Data;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.types.BringsClient;

@Data
public class ChildResponse {
    private long id;

    private String bringsClient;

    private CustomerResponse firstParent;

    private CustomerResponse secondParent;

    private String payerFullName;

    private String adultRequestForTherapyReason;

    private String adultRequestForTherapyDesiredOutcome;

    private String childExplanationForSeeingPsychologist;

    private String childDesiredChanges;
}
