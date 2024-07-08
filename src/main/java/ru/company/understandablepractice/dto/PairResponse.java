package ru.company.understandablepractice.dto;

import lombok.Data;

@Data
public class PairResponse{

    private long id;

    private CustomerResponse firstCustomer;

    private CustomerResponse secondCustomer;

    private String clientFirstRequestTherapyReason;

    private String clientFirstRequestTherapyDesiredOutcome;

    private String secondClientRequestTherapyReason;

    private String clientSecondRequestTherapyDesiredOutcome;

    private String familyStatus;

    private String fullNameCotherapy;

    private String phoneNumberCotherapy;

    private String mailCotherapy;

    private String financialTermsCotherapists;

    protected String clientType;

    protected String fullName;

    protected String firstName;

    protected String secondName;

    protected String lastName;

    protected String phoneNumber;

    protected String mail;

    protected String gender;
}
