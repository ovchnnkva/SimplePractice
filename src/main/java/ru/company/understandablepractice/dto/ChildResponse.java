package ru.company.understandablepractice.dto;

import lombok.Getter;
import lombok.Setter;
import ru.company.understandablepractice.model.types.ClientType;

@Getter
@Setter
public class ChildResponse extends PersonResponse {
    private String bringsClient;

    private CustomerResponse firstParent;

    private CustomerResponse secondParent;

    private String payerFullName;

    private String adultRequestForTherapyReason;

    private String adultRequestForTherapyDesiredOutcome;

    private String childExplanationForSeeingPsychologist;

    private String childDesiredChanges;

    public ChildResponse() {
        super(ClientType.CHILD.getTittle());
    }
}
