package ru.company.understandablepractice.dto.customers;

import lombok.Getter;
import lombok.Setter;
import ru.company.understandablepractice.dto.PersonResponse;
import ru.company.understandablepractice.model.types.ClientType;

@Getter
@Setter
public class PairApplicationDto extends CustomerApplicationDto{
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

    private String payerFullName;

    public PairApplicationDto() {
        super(ClientType.PAIR.getTittle());
    }

}
