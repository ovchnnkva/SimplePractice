package ru.company.understandablepractice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerResponse {

    private long id;

    private UserResponse user;

    private String clientStatus;

    private String contactMethod;

    private LocalDate dateFirstRequest;

    private LocalDate dateFirstConsultation;

    private String meetingFormat;

    private String onlinePlatform;

    private String offlineAddress;

    private String clientTherapyRequest;

    private String meetingTimeDay;

    private Integer financialCondition;

    private LocalDate birth;

    private String residenceAddress;

    private String peerRecommendation;

    private String specialTermsContact;

    private String familyStatus;

    private String priorityCommunicationChannel;

    private String supervisionStatusThisClient;

    private String contactSupervisor;

    private String supervisionMaterial;

    private String notes;
}
