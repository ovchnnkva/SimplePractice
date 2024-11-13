package ru.company.understandablepractice.dto.customers;

import lombok.Getter;
import lombok.Setter;
import ru.company.understandablepractice.dto.PersonResponse;
import ru.company.understandablepractice.model.types.ClientType;

@Getter
@Setter
public class ChildResponse extends CustomerResponse {
    private String bringsClient;

    private PersonResponse firstParent;

    private PersonResponse secondParent;

    private String payerFullName;

    private String adultRequestForTherapyReason;

    private String adultRequestForTherapyDesiredOutcome;

    private String childExplanationForSeeingPsychologist;

    private String childDesiredChanges;

    public ChildResponse() {
        super(ClientType.CHILD.getTittle());
    }
}
