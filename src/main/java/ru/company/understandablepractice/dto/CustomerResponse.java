package ru.company.understandablepractice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.company.understandablepractice.model.types.ClientType;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class CustomerResponse extends PersonResponse {

    private String contactMethod;

    private LocalDate dateFirstRequest;

    private LocalDate dateFirstConsultation;

    private String onlinePlatform;

    private String offlineAddress;

    private String clientTherapyRequest;

    private String meetingTimeDay;

    private Integer financialCondition;

    private String residenceAddress;

    private String peerRecommendation;

    private String specialTermsContact;

    private String familyStatus;

    private String priorityCommunicationChannel;

    private String supervisionStatusThisClient;

    private String contactSupervisor;

    private String supervisionMaterial;

    private String notes;

    public CustomerResponse() {
        super(ClientType.ADULT.getTittle());
    }
}
