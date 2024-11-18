package ru.company.understandablepractice.dto;

import lombok.Getter;
import lombok.Setter;
import ru.company.understandablepractice.model.types.ClientType;

@Getter
@Setter
public class PairResponse extends CustomerResponse {

    private PersonResponse secondPerson;

    private String clientFirstRequestTherapyReason;

    private String clientFirstRequestTherapyDesiredOutcome;

    private String secondClientRequestTherapyReason;

    private String clientSecondRequestTherapyDesiredOutcome;

    private String fullNameCotherapy;

    private String phoneNumberCotherapy;

    private String mailCotherapy;

    private String financialTermsCotherapists;

    private String familyStatus;

    public PairResponse() {
        super(ClientType.PAIR.getTittle());
    }
}
