package ru.company.understandablepractice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.company.understandablepractice.model.types.ClientType;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
public class CustomerResponse {

    protected long id;

    protected String clientType;

    protected String fullName;

    protected String firstName;

    protected String secondName;

    protected String lastName;

    protected LocalDate birth;

    protected String phoneNumber;

    protected String mail;

    protected String gender;

    protected String clientStatus;

    protected String meetingFormat;

    protected String contactMethod;

    protected LocalDate dateFirstRequest;

    protected LocalDate dateFirstConsultation;

    protected String onlinePlatform;

    protected String offlineAddress;

    protected String clientTherapyRequest;

    protected String meetingTimeDay;

    protected Integer financialCondition;

    protected String residenceAddress;

    protected String peerRecommendation;

    protected String specialTermsContact;

    protected String familyStatus;

    protected String priorityCommunicationChannel;

    protected String supervisionStatusThisClient;

    protected String contactSupervisor;

    protected String supervisionMaterial;

    protected String notes;

    public CustomerResponse() {
        this.clientType = ClientType.CUSTOMER.getTittle();
    }

    public CustomerResponse(String clientType) {
        this.clientType = clientType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerResponse that = (CustomerResponse) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
